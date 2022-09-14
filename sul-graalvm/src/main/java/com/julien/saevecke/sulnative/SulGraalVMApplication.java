package com.julien.saevecke.sulnative;

import com.julien.saevecke.sulnative.configurations.RabbitMQ;
import com.julien.saevecke.shared.messages.MembershipQuery;
import com.rabbitmq.client.Channel;
import de.learnlib.api.SUL;
import net.automatalib.words.WordBuilder;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class SulGraalVMApplication {
	private static long podStartTime;

	private boolean completedWork = false;

	@Autowired
	private SUL<String, String> sul;
	@Autowired
	private AmqpTemplate template;
	@Autowired
	private ApplicationContext appContext;

	@Value("${MY_POD_NAME:local}")
	private String hostname = "UNKNOWN";

	@Value("${ENABLE_SYMBOL_DELAY:false}")
	public boolean symbolDelayEnabled = false;

	@Value("${MAX_DELAY_PER_SYMBOL_MS:5000}")
	public int maxDelayPerSymbolMS = 5000;

	@Value("${MIN_DELAY_PER_SYMBOL_MS:3000}")
	public int minDelayPerSymbolMS = 3000;

	public static void
	main(String[] args) {
		podStartTime = System.nanoTime();
		SpringApplication.run(SulGraalVMApplication.class, args);
	}

	@EventListener
	public void handleContextRefreshEvent(ContextClosedEvent ctxClosedEvt) {
		if(completedWork) {

		} else {

		}
	}

	@RabbitListener(queues = RabbitMQ.SUL_INPUT_QUEUE)
	public void consume(MembershipQuery membershipQuery, Channel channel,
						@Header(AmqpHeaders.DELIVERY_TAG) long tag) {
		System.out.println("Hello my name is " + hostname + " - I'm a SUL devouring any query that comes across me!");

		long podCompletedTime = System.nanoTime();
		long podTimeElapsed = podCompletedTime - podStartTime;

		sul.pre();
		var completed = false;
		while(!completed) {
			System.out.println("I received a unique (" + membershipQuery.getUuid() + ") and delicious query: " + membershipQuery.getQuery().getPrefix() + " | " + membershipQuery.getQuery().getSuffix());

			long processingStartTime = System.nanoTime();

			if(symbolDelayEnabled) {
				long waitTime = membershipQuery.getQuery().getSuffix().stream().map(s->new Random().nextInt(maxDelayPerSymbolMS - minDelayPerSymbolMS + 1) + minDelayPerSymbolMS).reduce(0, Integer::sum);
				waitTime += membershipQuery.getQuery().getPrefix().stream().map(s->new Random().nextInt(maxDelayPerSymbolMS - minDelayPerSymbolMS + 1) + minDelayPerSymbolMS).reduce(0, Integer::sum);

				while(true){
					System.out.println("Sleeping: " + waitTime + "..");
					try {
						Thread.sleep(waitTime);
						break;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

			var query = membershipQuery.getQuery();
			var suffix = query.getSuffix(); // with output
			var prefix = query.getPrefix(); // without output

			try {
				for (var input : prefix) {
					sul.step(input);
				}

				var wb = new WordBuilder<String>(suffix.size());
				for (var input : suffix) {
					wb.add(sul.step(input));
				}

				query.setOutput(wb.toWord().asList());

				long processingCompletedTime = System.nanoTime();
				long processingTimeElapsed = processingCompletedTime - processingStartTime;

				var response = new MembershipQuery(membershipQuery.getUuid(), hostname, membershipQuery.getQuery(), podTimeElapsed, processingTimeElapsed);

				while(true){
					try {
						template.convertAndSend(
								RabbitMQ.SUL_DIRECT_EXCHANGE,
								RabbitMQ.SUL_OUTPUT_ROUTING_KEY,
								response
						);
						channel.basicAck(tag, false);
						completedWork = true;
						break;
					} catch (Exception e){
						e.printStackTrace();
						System.out.println("sending failed");
						//basic.Nack...
					}
				}
			} finally {
				sul.post();
			}

			completed = true;
		}

		System.out.println("I'm completely full.. I need a short nap.. Bye!");

		try {
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.exit(appContext, () -> 0);
		System.exit(0);
	}
}

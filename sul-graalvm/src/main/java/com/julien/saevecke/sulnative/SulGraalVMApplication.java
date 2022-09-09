package com.julien.saevecke.sulnative;

import com.julien.saevecke.sulnative.configurations.RabbitMQ;
import com.julien.saevecke.shared.messages.MembershipQuery;
import de.learnlib.api.SUL;
import net.automatalib.words.WordBuilder;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@SpringBootApplication
public class SulGraalVMApplication {
	private static long podStartTime;

	@Autowired
	private SUL<String, String> sul;
	@Autowired
	private AmqpTemplate template;
	@Autowired
	private ApplicationContext appContext;

	@Value("${MY_POD_NAME:local}")
	private String hostname = "UNKNOWN";

	@Value("${EXIT_STATUS:0}")
	private int exitStatus = 0;

	@Value("${ENABLE_DELAY:false}")
	public boolean delayEnabled = false;

	@Value("${DELAY_IN_SECONDS:0}")
	public int delayInSeconds = 0;

	@Value("${ENABLE_RANDOM_DELAY:false}")
	public boolean randomDelayEnabled = false;

	@Value("${MAX_RANDOM_DELAY_IN_SECONDS:5000}")
	public int maxRandomDelayInMS = 5000;

	@Value("${MIN_RANDOM_DELAY_IN_SECONDS:3000}")
	public int minRandomDelayInMS = 3000;

	public static void
	main(String[] args) {
		podStartTime = System.nanoTime();
		SpringApplication.run(SulGraalVMApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void consume() {
		System.out.println("Hello my name is " + hostname + " - I'm a SUL devouring any query that comes across me!");

		long podCompletedTime = System.nanoTime();
		long podTimeElapsed = podCompletedTime - podStartTime;

		sul.pre();
		var completed = false;
		while(!completed) {

			Object message = null;

			try {
				message = template.receiveAndConvert(RabbitMQ.SUL_INPUT_QUEUE);
			} catch (Exception e){
				e.printStackTrace();
				System.out.println("receiving failed");
			}
			if (message == null) {
				continue;
			}

			var membershipQuery = (MembershipQuery) message;

			System.out.println("I received a unique (" + membershipQuery.getUuid() + ") and delicious query: " + membershipQuery.getQuery().getPrefix() + " | " + membershipQuery.getQuery().getSuffix());

			long processingStartTime = System.nanoTime();

			long waitTime = membershipQuery.getQuery().getSuffix().stream().map(s->new Random().nextInt(maxRandomDelayInMS - minRandomDelayInMS + 1) + minRandomDelayInMS).reduce(0, Integer::sum);
			waitTime += membershipQuery.getQuery().getPrefix().stream().map(s->new Random().nextInt(maxRandomDelayInMS - minRandomDelayInMS + 1) + minRandomDelayInMS).reduce(0, Integer::sum);

			if(delayEnabled) {
				while(true){
					System.out.println("Sleeping: " + waitTime + "..");
					try {
						Thread.sleep(waitTime);
						break;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				/*var delayMessageSuffix = " should suffice till it has the right temperature to be eaten!";
				var delayMessagePrefix = "The query is still too hot for me..";
				try {
					if(randomDelayEnabled) {
						delayMessagePrefix += " I guess ";
						delayInSeconds = new Random().nextInt(maxRandomDelayInMS - minRandomDelayInMS + 1) + minRandomDelayInMS;
					} else {
						delayMessagePrefix += " I'm pretty sure ";
					}
					System.out.println(delayMessagePrefix + delayInSeconds + " seconds" + delayMessageSuffix);
					TimeUnit.SECONDS.sleep(delayInSeconds);
					System.out.println("I've waited long enough - time to eat!");
				} catch (InterruptedException ie) {
					ie.printStackTrace();
					Thread.currentThread().interrupt();
				}*/
			}

			var query = membershipQuery.getQuery();
			var suffix = query.getSuffix(); // with output
			var prefix = query.getPrefix(); // without output

			try {
				for (var input : prefix) {
					sul.step(input);
				}

				// Suffix: Execute symbols, outputs constitute output word
				var wb = new WordBuilder<String>(suffix.size());
				for (var input : suffix) {
					wb.add(sul.step(input));
				}

				query.setOutput(wb.toWord().asList());

				long processingCompletedTime = System.nanoTime();
				long processingTimeElapsed = processingCompletedTime - processingStartTime;

				var response = new MembershipQuery(membershipQuery.getUuid(), hostname, membershipQuery.getQuery(), podTimeElapsed, processingTimeElapsed);

				try {
					template.convertAndSend(
							RabbitMQ.SUL_DIRECT_EXCHANGE,
							RabbitMQ.SUL_OUTPUT_ROUTING_KEY,
							response
					);
				} catch (Exception e){
					e.printStackTrace();
					System.out.println("sending failed");
				}
			} finally {
				sul.post();
			}

			completed = true;
		}

		System.out.println("I'm completely full.. I need a short nap.. Bye!");

		SpringApplication.exit(appContext, () -> exitStatus);
		System.exit(exitStatus);
	}
}

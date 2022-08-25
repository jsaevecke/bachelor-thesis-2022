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

	@Value("${MAX_RANDOM_DELAY_IN_SECONDS:5}")
	public int maxRandomDelayInSeconds = 5;

	@Value("${MIN_RANDOM_DELAY_IN_SECONDS:1}")
	public int minRandomDelayInSeconds = 1;

	public static void
	main(String[] args) {
		podStartTime = System.nanoTime();
		SpringApplication.run(SulGraalVMApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void consume() {
		long podCompletedTime = System.nanoTime();
		long podTimeElapsed = podCompletedTime - podStartTime;

		sul.pre();
		var completed = false;
		while(!completed) {
			var message = template.receiveAndConvert(RabbitMQ.SUL_INPUT_QUEUE);
			if (message == null) {
				continue;
			}
			var membershipQuery = (MembershipQuery) message;

			System.out.println(hostname + " received message with uuid: " + membershipQuery.getUuid());

			long processingStartTime = System.nanoTime();

			if(delayEnabled) {
				try {
					if(randomDelayEnabled) {
						delayInSeconds = new Random().nextInt(maxRandomDelayInSeconds - minRandomDelayInSeconds + 1) + minRandomDelayInSeconds;
					}
					TimeUnit.SECONDS.sleep(delayInSeconds);
				} catch (InterruptedException ie) {
					Thread.currentThread().interrupt();
				}
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
				template.convertAndSend(
						RabbitMQ.SUL_DIRECT_EXCHANGE,
						RabbitMQ.SUL_OUTPUT_ROUTING_KEY,
						response
				);
			} finally {
				sul.post();
			}

			completed = true;
		}

		SpringApplication.exit(appContext, () -> exitStatus);
		System.exit(exitStatus);
	}
}

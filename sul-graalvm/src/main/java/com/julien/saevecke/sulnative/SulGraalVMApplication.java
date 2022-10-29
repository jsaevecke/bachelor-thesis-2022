package com.julien.saevecke.sulnative;

import com.julien.saevecke.sulnative.configurations.RabbitMQ;
import com.julien.saevecke.shared.messages.MembershipQuery;
import com.julien.saevecke.sulnative.mealy.Experiment;
import com.rabbitmq.client.Channel;
import de.learnlib.api.SUL;
import de.learnlib.driver.util.MealySimulatorSUL;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.nativex.hint.ResourceHint;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@SpringBootApplication
public class SulGraalVMApplication {
	private static long podStartTime;
	private static long podTotalStartUpTime = 0L;

	private SUL<String, String> sul;
	@Autowired
	private AmqpTemplate template;
	@Autowired
	private ApplicationContext appContext;

	@Value("${ENABLE_ONE_USE:false}")
	public boolean oneUseEnabled = true;

	@Value("${MY_POD_NAME:local}")
	private String hostname = "UNKNOWN";

	@Value("${ENABLE_SYMBOL_DELAY:false}")
	public boolean symbolDelayEnabled = false;

	@Value("${MAX_DELAY_PER_SYMBOL_MS:5000}")
	public int maxDelayPerSymbolMS = 5000;

	@Value("${MIN_DELAY_PER_SYMBOL_MS:3000}")
	public int minDelayPerSymbolMS = 3000;

	@Value("${MEALY_MACHINE_TYPE:coffee}")
	private String mealyMachineType = "coffee";

	@Value("${ENABLE_CREATE_FINISHED_FILE:false}")
	public boolean createFinishedFileEnabled = true;

	@Value("${FINISHED_FILE_PATH:/cache}")
	private String finishedFilePath = "/cache";

	@Value("${FILE_COMMAND:sudo touch /root/finished}")
	private String fileCommand = "sudo touch /root/finished";

	// kubectl exec -it --namespace=tools mongo-pod -- bash -c "mongo"

	public static void main(String[] args) {
		podStartTime = System.nanoTime();
		SpringApplication.run(SulGraalVMApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void handleApplicationReadyEvent() throws IOException {
		sul = new MealySimulatorSUL<>(new Experiment().create(mealyMachineType));
		long podCompletedTime = System.nanoTime();
		podTotalStartUpTime = podCompletedTime - podStartTime;
	}

	@RabbitListener(queues = RabbitMQ.SUL_INPUT_QUEUE)
	public void consume(MembershipQuery membershipQuery, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
		System.out.println("Hello my name is " + hostname + " - I'm a SUL devouring any query that comes across me!");

		sul.pre();
		System.out.println("I received a unique (" + membershipQuery.getUuid() + ") and delicious query: " + membershipQuery.getQuery().getPrefix() + " | " + membershipQuery.getQuery().getSuffix());

		long processingStartTime = System.nanoTime();

		long waitTime = 0;
		if(symbolDelayEnabled) {
			waitTime = membershipQuery.getQuery().getSuffix().stream().map(s->new Random().nextInt(maxDelayPerSymbolMS - minDelayPerSymbolMS + 1) + minDelayPerSymbolMS).reduce(0, Integer::sum);
			waitTime += membershipQuery.getQuery().getPrefix().stream().map(s->new Random().nextInt(maxDelayPerSymbolMS - minDelayPerSymbolMS + 1) + minDelayPerSymbolMS).reduce(0, Integer::sum);

			while(true){
				System.out.println("I need to digest for: " + waitTime + "ms..");
				try {
					Thread.sleep(waitTime);
					break;
				} catch (InterruptedException e) {
					e.printStackTrace();
					channel.basicNack(tag, false, true);
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

			membershipQuery.setPodName(hostname);
			membershipQuery.setPodStartUpTime(podTotalStartUpTime);
			membershipQuery.setPodProcessingTime(processingTimeElapsed);
			membershipQuery.setPodWaitTime(waitTime);

			while(true){
				try {
					template.convertAndSend(
							RabbitMQ.SUL_DIRECT_EXCHANGE,
							RabbitMQ.SUL_OUTPUT_ROUTING_KEY,
							membershipQuery
					);
					channel.basicAck(tag, false);
					System.out.println("That was delicious!");
					break;
				} catch (Exception e){
					e.printStackTrace();
					System.out.println("sending failed");
					channel.basicNack(tag, false, true);
				}
			}
		} finally {
			sul.post();
		}

		/*if (oneUseEnabled) {
			System.out.println("I'm completely full.. I need a short nap.. Bye!");
			if(createFinishedFileEnabled) {
				ProcessBuilder processBuilder = new ProcessBuilder();
				processBuilder.directory(new File(finishedFilePath));
				processBuilder.command("/bin/sh", "-c", fileCommand);
				try {
					processBuilder.start();
					System.out.println("worked - processbuild! - " + finishedFilePath);
				} catch (IOException e) {
					e.printStackTrace();
				}

				try {
					var path = finishedFilePath + "/finished";
					File file = new File(path);
					file.createNewFile();
					System.out.println("worked - file!" + finishedFilePath);
				} catch(Exception e) {
					e.printStackTrace();
				}
			}
		}*/
		try {
			channel.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		SpringApplication.exit(appContext, () -> 0);
		System.exit(0);
	}
}

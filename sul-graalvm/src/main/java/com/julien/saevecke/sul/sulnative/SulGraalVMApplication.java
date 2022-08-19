package com.julien.saevecke.sul.sulnative;

import com.julien.saevecke.shared.configurations.RabbitMQ;
import com.julien.saevecke.shared.messages.MembershipQuery;
import de.learnlib.api.SUL;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.nativex.hint.SerializationHint;
import org.springframework.nativex.hint.TypeAccess;
import org.springframework.nativex.hint.TypeHint;

import java.util.ArrayList;


@TypeHint(types = {ArrayList.class, MembershipQuery.class}, access = {TypeAccess.DECLARED_CLASSES, TypeAccess.DECLARED_FIELDS, TypeAccess.DECLARED_CONSTRUCTORS, TypeAccess.DECLARED_METHODS, TypeAccess.PUBLIC_CLASSES, TypeAccess.PUBLIC_CONSTRUCTORS, TypeAccess.PUBLIC_FIELDS, TypeAccess.PUBLIC_METHODS})
@SerializationHint(types = {MembershipQuery.class,ArrayList.class})
@EnableRabbit
@SpringBootApplication
public class SulGraalVMApplication {
	@Autowired
	private SUL<String, String> sul;
	@Autowired
	private AmqpTemplate template;
	@Autowired
	private ApplicationContext appContext;

	//@Value("${MY_POD_NAME}")
	private String hostname = "UNKNOWN";

	public static void main(String[] args) {
		SpringApplication.run(SulGraalVMApplication.class, args);
	}

	@EventListener(ApplicationReadyEvent.class)
	public void consume() {
		sul.pre();
		var completed = false;
		while(!completed) {
			var message = template.receiveAndConvert(RabbitMQ.SUL_INPUT_QUEUE);
			if (message == null) {
				continue;
			}

			var membershipQuery = (MembershipQuery) message;

			System.out.println(hostname + " received message with uuid: " + membershipQuery.getUuid());

			var suffix = membershipQuery.getSuffix(); // with output
			var prefix = membershipQuery.getPrefix(); // without output

			try {
				for (var input : prefix) {
					sul.step(input);
				}

				// Suffix: Execute symbols, outputs constitute output word
				/*var wb = new WordBuilder<String>(suffix.size());
				for (var input : suffix) {
					wb.add(sul.step(input));
				}

				query.setOutput(wb.toWord().asList());*/

				var output = membershipQuery.getOutput();

				// Suffix: Execute symbols, outputs constitute output word
				for (var input : suffix) {
					output.add(sul.step(input));
				}

				membershipQuery.setOutput(output);
				membershipQuery.setPodName(hostname);

				template.convertAndSend(
						RabbitMQ.SUL_DIRECT_EXCHANGE,
						RabbitMQ.SUL_OUTPUT_ROUTING_KEY,
						membershipQuery
				);
			} finally {
				sul.post();
			}

			completed = true;
		}

		SpringApplication.exit(appContext, () -> 0);
		System.exit(0);
	}
}

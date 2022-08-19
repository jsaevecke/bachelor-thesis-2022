package com.julien.saevecke.learner.sul.processors;

import com.julien.saevecke.learner.sul.config.rabbitmq.RabbitMQConfig;
import com.julien.saevecke.learner.sul.messages.MembershipQuery;

import de.learnlib.api.SUL;

import net.automatalib.words.WordBuilder;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class QueryProcessor {
    @Autowired
    private SUL<String, String> sul;
    @Autowired
    private AmqpTemplate template;
    @Autowired
    private ApplicationContext appContext;

    @Value("${MY_POD_NAME}")
    private String hostname;

    @EventListener(ApplicationReadyEvent.class)
    public void consume() {
        sul.pre();
        var completed = false;
        while(!completed) {
            var message = template.receiveAndConvert(RabbitMQConfig.SUL_INPUT_QUEUE);
            if (message == null) {
                continue;
            }

            var membershipQuery = (MembershipQuery) message;

            System.out.println(hostname + " received message with uuid: " + membershipQuery.getUuid());

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

                var response = new MembershipQuery(membershipQuery.getUuid(), hostname, membershipQuery.getDelayInSeconds(), membershipQuery.getQuery());

                template.convertAndSend(
                        RabbitMQConfig.SUL_DIRECT_EXCHANGE,
                        RabbitMQConfig.SUL_OUTPUT_ROUTING_KEY,
                        response
                );
            } finally {
                sul.post();
            }

            completed = true;
        }

        SpringApplication.exit(appContext, () -> 0);
        System.exit(0);
    }

    /*
    @RabbitListener(queues = RabbitMQConfig.SUL_INPUT_QUEUE)
    public void consume(MembershipQuery membershipQuery) {
        sul.pre();

        System.out.println(hostname + " received message with uuid: " + membershipQuery.getUuid());

        var query = membershipQuery.getQuery();
        var suffix = query.getSuffix(); // with output
        var prefix = query.getPrefix(); // without output

        // artificial delay for testing purposes - kubernetes autoscaling
        var delayInSeconds = membershipQuery.getDelayInSeconds();
        if(delayInSeconds >= 1) {
            try {
                TimeUnit.SECONDS.sleep(delayInSeconds);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

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

            var response = new MembershipQuery(membershipQuery.getUuid(), hostname, membershipQuery.getDelayInSeconds(), membershipQuery.getQuery());

            template.convertAndSend(
                    RabbitMQConfig.SUL_DIRECT_EXCHANGE,
                    RabbitMQConfig.SUL_OUTPUT_ROUTING_KEY,
                    response
            );
        } finally {
            sul.post();
        }

        SpringApplication.exit(appContext, () -> 0);
        System.exit(0);
    }*/
}

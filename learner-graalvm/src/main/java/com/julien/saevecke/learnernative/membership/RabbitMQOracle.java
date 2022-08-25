package com.julien.saevecke.learnernative.membership;

import com.julien.saevecke.learnernative.configurations.RabbitMQ;
import com.julien.saevecke.shared.messages.MembershipQuery;
import de.learnlib.api.oracle.MembershipOracle.MealyMembershipOracle;
import de.learnlib.api.query.DefaultQuery;
import de.learnlib.api.query.Query;
import net.automatalib.words.Word;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Component
public class RabbitMQOracle implements MealyMembershipOracle<String, String> {
    public static final String UNKNOWN = "unknown";
    public static final int DELAY_IN_SECONDS = 0;

    @Autowired
    AmqpTemplate template;

    HashMap<String, DefaultQuery<String, Word<String>>> sentQueries = new HashMap<>();

    @Override
    public void processQueries(Collection<? extends Query<String, Word<String>>> queries) {
        for (Query<String, Word<String>> rawQuery : queries) {
            var uuid = UUID.randomUUID().toString();
            var defaultQuery = (DefaultQuery<String, Word<String>>)rawQuery;

            var queryPrefix = defaultQuery.getPrefix().asList();
            var querySuffix = defaultQuery.getSuffix().asList();
            ArrayList<String> prefix = new ArrayList<>(queryPrefix.size());
            ArrayList<String> suffix = new ArrayList<>(querySuffix.size());

            for(int i = 0; i < querySuffix.size(); i++) {
                suffix.add(i, querySuffix.get(i));
            }

            for(int i = 0; i < queryPrefix.size(); i++) {
                prefix.add(i, queryPrefix.get(i));
            }

            var query = new MembershipQuery(uuid, UNKNOWN, DELAY_IN_SECONDS, prefix, suffix, new ArrayList<>());
            sentQueries.put(uuid, defaultQuery);

            System.out.println("Sent query: " + defaultQuery.getPrefix() + " | " + defaultQuery.getSuffix());

            template.convertAndSend(
                    RabbitMQ.SUL_DIRECT_EXCHANGE,
                    RabbitMQ.SUL_INPUT_ROUTING_KEY,
                    query
            );
        }

        var latch = new CountDownLatch(1);

        // wait until all queries are answered
        Thread newThread = new Thread(()->{
            var completed = false;
            var queriesAnswered = 0;

            while(!completed) {
                completed = true;
                var message = template.receiveAndConvert(RabbitMQ.SUL_OUTPUT_QUEUE);
                if (message == null) {
                    completed = false;
                    continue;
                }

                var query = (MembershipQuery)message;

                if(sentQueries.containsKey(query.getUuid())) {
                    var defaultQuery = sentQueries.get(query.getUuid());
                    defaultQuery.answer(Word.fromList(query.getOutput()));
                    queriesAnswered++;

                    if(queriesAnswered != sentQueries.size())
                        completed = false;

                    System.out.println("Received from " + query.getPodName() + ": " + query.getPrefix() + " | " + query.getSuffix() + " --> " + query.getOutput());
                } else {
                    System.out.println("Unknown message received - drop!");
                    completed = false;
                }
            }

            sentQueries.clear();

            latch.countDown();
        });

        newThread.start();

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

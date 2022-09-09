package com.julien.saevecke.learnerjvm.membership;

import com.julien.saevecke.learnerjvm.configurations.RabbitMQ;
import com.julien.saevecke.learnerjvm.statistics.Statistics;
import com.julien.saevecke.shared.messages.MembershipQuery;
import com.julien.saevecke.shared.messages.DefaultQueryProxy;
import de.learnlib.api.oracle.MembershipOracle.MealyMembershipOracle;
import de.learnlib.api.query.DefaultQuery;
import de.learnlib.api.query.Query;
import net.automatalib.words.Word;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Component
public class RabbitMQOracle implements MealyMembershipOracle<String, String> {
    @Autowired
    private Statistics statistics;
    @Autowired
    private AmqpTemplate template;

    private final HashMap<UUID, Query<String, Word<String>>> sentQueries = new HashMap<>();

    @Override
    public void processQueries(Collection<? extends Query<String, Word<String>>> queries) {
        var batchSize = queries.size();
        System.out.println("Got a nice bunch of delicious of " + batchSize + " queries");

        for (Query<String, Word<String>> rawQuery : queries) {
            var uuid = UUID.randomUUID();
            var defaultQuery = new DefaultQuery<String, Word<String>>(rawQuery.getInput());
            var query = new MembershipQuery(uuid, DefaultQueryProxy.createFrom(defaultQuery));

            sentQueries.put(uuid, rawQuery);

            System.out.println("Distributing delicious query: " + query.getQuery().getPrefix() + " | " + query.getQuery().getSuffix());

            template.convertAndSend(
                    RabbitMQ.SUL_DIRECT_EXCHANGE,
                    RabbitMQ.SUL_INPUT_ROUTING_KEY,
                    query
            );
        }

        var latch = new CountDownLatch(1);
        Thread newThread = new Thread(()->{
            long responseStartTime = System.nanoTime(); // statistics

            System.out.println("Waiting for my precious consumers finishing their delicious query..");
            while(!sentQueries.isEmpty()) {
                var message = template.receiveAndConvert(RabbitMQ.SUL_OUTPUT_QUEUE);
                if (message == null) {
                    continue;
                }

                var query = (MembershipQuery)message;
                if(sentQueries.containsKey(query.getUuid())) {
                    long responseCompletedTime = System.nanoTime();
                    long responseTimeElapsed = responseCompletedTime - responseStartTime;

                    if(statistics.maxNextResponseTime < responseTimeElapsed){
                        statistics.maxNextResponseTime = responseTimeElapsed;
                    }
                    if(statistics.minNextResponseTime > responseTimeElapsed) {
                        statistics.minNextResponseTime = responseTimeElapsed;
                    }
                    statistics.averageNextResponseTime += responseTimeElapsed;

                    responseStartTime = System.nanoTime();

                    var sentQuery = sentQueries.remove(query.getUuid());
                    sentQuery.answer(Word.fromList(query.getQuery().getOutput()));

                    System.out.println("My precious consumer " + query.getPodName() + "left behind a review! \n Review: " +
                            query.getQuery().getPrefix() + " | " +
                            query.getQuery().getSuffix() + " :: " +
                            query.getQuery().getOutput());
                } else {
                    System.out.println("This finished query is not from my consumers. Just throwing it away!");
                }

                var startUpTime = query.getPodStartUpTime();
                var processingTime = query.getPodProcessingTime();
                statistics.averageStartUpTime += startUpTime;
                statistics.averageProcessingTime += processingTime;
                if(statistics.maxStartUpTime < startUpTime){
                    statistics.maxStartUpTime = startUpTime;
                }
                if(statistics.minStartUpTime > startUpTime) {
                    statistics.minStartUpTime = startUpTime;
                }
                if(statistics.maxProcessingTime < processingTime){
                    statistics.maxProcessingTime = processingTime;
                }
                if(statistics.minProcessingTime > processingTime) {
                    statistics.minProcessingTime = processingTime;
                }

                System.out.println("Still waiting for " + sentQueries.size() + " consumers to be finished with their delicious query..");
            }

            latch.countDown();
        });

        long batchStartTime = System.nanoTime();
        newThread.start();
        try {
            // wait until the whole batch of queries is answered
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("I'm happy that all my delicious queries got eaten - going back to work!");

        long batchCompletedTime = System.nanoTime();
        long batchTimeElapsed = batchCompletedTime - batchStartTime;

        // accumulate statistics for evaluation
        statistics.totalSentQueries += batchSize;
        statistics.averageBatchSize += batchSize; // will be divides by total batches
        if(statistics.maxBatchSize < batchSize){
            statistics.maxBatchSize = batchSize;
        }
        if(statistics.minBatchSize > batchSize) {
            statistics.minBatchSize = batchSize;
        }
        if(statistics.maxBatchProcessingTime < batchTimeElapsed){
            statistics.maxBatchProcessingTime = batchTimeElapsed;
        }
        if(statistics.minBatchProcessingTime > batchTimeElapsed) {
            statistics.minBatchProcessingTime = batchTimeElapsed;
        }

        statistics.averageBatchProcessingTime += batchTimeElapsed; // will be divded by total batches
        statistics.totalBatches++;
    }
}

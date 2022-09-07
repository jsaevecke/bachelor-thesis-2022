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

import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

@Component
public class RabbitMQOracle implements MealyMembershipOracle<String, String> {
    @Autowired
    Statistics statistics;
    @Autowired
    AmqpTemplate template;

    HashMap<UUID, Query<String, Word<String>>> sentQueries = new HashMap<>();

    @Override
    public void processQueries(Collection<? extends Query<String, Word<String>>> queries) {
        var batchSize = queries.size();
        for (Query<String, Word<String>> rawQuery : queries) {
            var uuid = UUID.randomUUID();
            var defaultQuery = new DefaultQuery<String, Word<String>>(rawQuery.getInput());//(DefaultQuery<String, Word<String>>)rawQuery;
            var query = new MembershipQuery(uuid, DefaultQueryProxy.createFrom(defaultQuery));
            sentQueries.put(uuid, rawQuery);

            System.out.println("Sent query: " + query.getQuery().getPrefix() + " | " + query.getQuery().getSuffix());

            template.convertAndSend(
                    RabbitMQ.SUL_DIRECT_EXCHANGE,
                    RabbitMQ.SUL_INPUT_ROUTING_KEY,
                    query
            );
        }

        var latch = new CountDownLatch(1);
        Thread newThread = new Thread(()->{
            long responseStartTime = System.nanoTime(); // statistics
            var completed = false;
            var queriesAnswered = 0;

            while(!completed && sentQueries.size() > 0) {
                completed = true;
                var message = template.receiveAndConvert(RabbitMQ.SUL_OUTPUT_QUEUE);
                if (message == null) {
                    completed = false;
                    continue;
                }

                System.out.println("Answered: " + queriesAnswered + " - Sent: " + sentQueries.size());

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

                    var sentQuery = sentQueries.get(query.getUuid());
                    sentQuery.answer(Word.fromList(query.getQuery().getOutput()));
                    queriesAnswered++;

                    if(queriesAnswered != sentQueries.size())
                        completed = false;

                    System.out.println("Received from " + query.getPodName() + ": " + query.getQuery().getPrefix() + " | " + query.getQuery().getSuffix() + " --> " + query.getQuery().getOutput());
                } else {
                    System.out.println("Unknown message received - drop!");
                    completed = false;
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
            }

            sentQueries.clear();

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
        long batchCompletedTime = System.nanoTime();
        long batchTimeElapsed = batchCompletedTime - batchStartTime;

        // accumelate statistics for evaluation
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

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
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
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
    @Autowired
    private ApplicationContext appContext;

    private final HashMap<UUID, Query<String, Word<String>>> sentQueries = new HashMap<>();

    @Override
    public void processQueries(Collection<? extends Query<String, Word<String>>> queries) {
        var batchSize = queries.size();
        for (Query<String, Word<String>> rawQuery : queries) {
            var uuid = UUID.randomUUID();
            var defaultQuery = new DefaultQuery<String, Word<String>>(rawQuery.getInput());
            var query = new MembershipQuery(uuid, DefaultQueryProxy.createFrom(defaultQuery));

            sentQueries.put(uuid, rawQuery);

            var sequenceLength = query.getQuery().getPrefix().size() + query.getQuery().getSuffix().size();
            statistics.averageSequenceLength += sequenceLength;
            if(statistics.maxSequenceLength < sequenceLength){
                statistics.maxSequenceLength = sequenceLength;
            }
            if(statistics.minSequenceLength > sequenceLength){
                statistics.minSequenceLength = sequenceLength;
            }

            while(true){
                try {
                    template.convertAndSend(
                            RabbitMQ.SUL_DIRECT_EXCHANGE,
                            RabbitMQ.SUL_INPUT_ROUTING_KEY,
                            query
                    );
                    break;
                } catch (Exception e){
                    e.printStackTrace();
                    System.out.println("Sending failed!");
                }
            }
        }

        var latch = new CountDownLatch(1);
        Thread newThread = new Thread(()->{
            long responseStartTime = System.nanoTime(); // statistics
            long bailOutStartTime = System.currentTimeMillis();
            long last = 0;
            while(!sentQueries.isEmpty()) {
                Object message = null;
                while(true){ // 90 seconds
                    try {
                        message = template.receiveAndConvert(RabbitMQ.SUL_OUTPUT_QUEUE);
                        break;
                    } catch (Exception e){
                        e.printStackTrace();
                        System.out.println("Receiving Failed!");
                    }
                }

                if (message == null) {
                    var elapsedTime = (System.currentTimeMillis()-bailOutStartTime)/1000;
                    if((last != elapsedTime) && (elapsedTime > 0) && (elapsedTime % 30 == 0)) {
                        last = elapsedTime;
                        System.out.println(elapsedTime + " without response (max. 90 seconds).");
                    }
                    if(elapsedTime >= 90){
                        System.out.println("Something went wrong... 90 seconds without response... bailing out!");
                        SpringApplication.exit(appContext, () -> 0);
                        System.exit(0);
                    }
                    continue;
                }
                last = 0;
                bailOutStartTime = System.nanoTime();
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
                } else {
                    System.out.println("This finished query is not from my consumers. Just throwing it away!");
                }

                var startUpTime = query.getPodStartUpTime();
                var processingTime = query.getPodProcessingTime();
                var waitTime = query.getPodWaitTime();
                statistics.averageStartUpTime += startUpTime;
                statistics.averageProcessingTime += processingTime;
                statistics.averageWaitTime += waitTime;
                if(statistics.maxWaitTime < waitTime){
                    statistics.maxWaitTime = waitTime;
                }
                if(statistics.minWaitTime > waitTime) {
                    statistics.minWaitTime = waitTime;
                }
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

                statistics.totalSentQueries += 1;
                System.out.println("Processed queries: " + statistics.totalSentQueries + "(" + sentQueries.size() + "/" + batchSize + ")");
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

        long batchCompletedTime = System.nanoTime();
        long batchTimeElapsed = batchCompletedTime - batchStartTime;

        // accumulate statistics for evaluation
        statistics.averageBatchSize += batchSize; // will be divides by total batches
        if(statistics.maxBatchSize < batchSize){
            statistics.maxBatchSize = batchSize;
        }
        if(statistics.minBatchSize > batchSize && batchSize > 0) {
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

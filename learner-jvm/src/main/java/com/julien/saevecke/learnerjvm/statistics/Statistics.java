package com.julien.saevecke.learnerjvm.statistics;

import org.springframework.stereotype.Component;

@Component
public class Statistics {
    public long averageProcessingTime = 0;
    public long averageStartUpTime = 0;
    public long averageNextResponseTime = 0;
    public long averageBatchProcessingTime = 0;
    public float averageBatchSize = 0;
    public long averageWaitTime = 0;
    public float averageSequenceLength = 0;

    public long maxWaitTime = 0;
    public long minWaitTime = Long.MAX_VALUE;
    public long minSequenceLength = Long.MAX_VALUE;
    public long maxSequenceLength = 0;

    public long maxProcessingTime = 0;
    public long minProcessingTime = Long.MAX_VALUE;
    public long maxStartUpTime = 0;
    public long minStartUpTime = Long.MAX_VALUE;

    public long maxNextResponseTime = 0;
    public long minNextResponseTime = Long.MAX_VALUE;
    public long minBatchProcessingTime = Long.MAX_VALUE;
    public long maxBatchProcessingTime = 0;
    public int maxBatchSize = 0;
    public int minBatchSize = Integer.MAX_VALUE;

    public int totalSentQueries = 0;
    public int totalBatches = 0;
    public long totalLearnTime = 0; //ms

    @Override
    public String toString() {
        return  "," + minProcessingTime +
                "," + maxProcessingTime +
                "," + averageProcessingTime +
                "," + minStartUpTime +
                "," + maxStartUpTime +
                "," + averageStartUpTime +
                "," + minNextResponseTime +
                "," + maxNextResponseTime +
                "," + averageNextResponseTime +
                "," + minBatchProcessingTime +
                "," + maxBatchProcessingTime +
                "," + averageBatchProcessingTime +
                "," + minBatchSize +
                "," + maxBatchSize +
                "," + averageBatchSize +
                "," + minSequenceLength +
                "," + maxSequenceLength +
                "," + averageSequenceLength +
                "," + minWaitTime +
                "," + maxWaitTime +
                "," + averageWaitTime +
                "," + totalSentQueries +
                "," + totalBatches +
                "," + totalLearnTime;
    }
}

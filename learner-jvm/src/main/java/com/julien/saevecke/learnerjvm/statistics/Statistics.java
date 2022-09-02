package com.julien.saevecke.learnerjvm.statistics;

import org.springframework.stereotype.Component;

@Component
public class Statistics {
    public long averageProcessingTime = 0;
    public long averageStartUpTime = 0;
    public long averageNextResponseTime = 0;
    public long averageBatchProcessingTime = 0;
    public int averageBatchSize = 0;

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
        return "Statistics{" +
                "averageProcessingTime=" + averageProcessingTime +
                ", averageStartUpTime=" + averageStartUpTime +
                ", averageNextResponseTime=" + averageNextResponseTime +
                ", averageBatchProcessingTime=" + averageBatchProcessingTime +
                ", averageBatchSize=" + averageBatchSize +
                ", maxProcessingTime=" + maxProcessingTime +
                ", minProcessingTime=" + minProcessingTime +
                ", maxStartUpTime=" + maxStartUpTime +
                ", minStartUpTime=" + minStartUpTime +
                ", maxNextResponseTime=" + maxNextResponseTime +
                ", minNextResponseTime=" + minNextResponseTime +
                ", minBatchProcessingTime=" + minBatchProcessingTime +
                ", maxBatchProcessingTime=" + maxBatchProcessingTime +
                ", maxBatchSize=" + maxBatchSize +
                ", minBatchSize=" + minBatchSize +
                ", totalSentQueries=" + totalSentQueries +
                ", totalBatches=" + totalBatches +
                ", totalLearnTime=" + totalLearnTime +
                '}';
    }
}

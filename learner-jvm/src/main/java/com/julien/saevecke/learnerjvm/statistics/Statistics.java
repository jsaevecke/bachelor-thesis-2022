package com.julien.saevecke.learnerjvm.statistics;

import org.springframework.stereotype.Component;

@Component
public class Statistics {
    public long averageProcessingTime = 0; //ms
    public long averageStartUpTime = 0; //ms
    public long averageNextResponseTime = 0; //ms
    public long averageBatchProcessingTime = 0; //ms
    public int averageBatchSize = 0;

    public long maxProcessingTime = 0; //ms
    public long minProcessingTime = 0; //ms
    public long maxStartUpTime = 0; //ms
    public long minStartUpTime = 0; //ms

    public long maxNextResponseTime = 0; //ms
    public long minNextResponseTime = 0; //ms
    public long minBatchProcessingTime = 0; //ms
    public long maxBatchProcessingTime = 0; //ms
    public int maxBatchSize = 0;
    public int minBatchSize = 0;

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

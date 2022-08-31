package com.julien.saevecke.learnerjvm.mealy;

import com.julien.saevecke.learnerjvm.membership.RabbitMQOracle;
import com.julien.saevecke.learnerjvm.statistics.Statistics;
import de.learnlib.algorithms.dhc.mealy.MealyDHC;
import de.learnlib.api.query.DefaultQuery;
import de.learnlib.oracle.equivalence.WpMethodEQOracle;
import net.automatalib.serialization.dot.GraphDOT;
import net.automatalib.words.Word;
import net.automatalib.words.impl.Alphabets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.StringWriter;

@Component
public class Coffee {
    public static final String POD = "POD";
    public static final String CLEAN = "CLEAN";
    public static final String WATER = "WATER";
    public static final String BUTTON = "BUTTON";
    public static final int CONVERT_TO_MS = 1000000;

    @Autowired
    RabbitMQOracle membershipOracle;
    @Autowired
    Statistics statistics;

    public void learn() {
        var alphabet = Alphabets.fromArray(POD, CLEAN, WATER, BUTTON);
        var learner = new MealyDHC<>(alphabet, membershipOracle);
        //TTTLearnerMealy<>
        var eq = new WpMethodEQOracle<>(membershipOracle, 3);

        DefaultQuery<String, Word<String>> counterexample = null;
        long start = System.nanoTime();
        do {
            if(counterexample == null) {
                learner.startLearning();
            } else {
                boolean refined = learner.refineHypothesis(counterexample);
                if (!refined) {
                    System.err.println("No refinement effected by counterexample!");
                }
            }

            counterexample = eq.findCounterExample(learner.getHypothesisModel(), alphabet);

        } while (counterexample != null);
        System.out.println("Learning complete");

        long finish = System.nanoTime();
        long timeElapsed = finish - start;

        final var model = learner.getHypothesisModel();

        try {
            final var sw = new StringWriter();
            GraphDOT.write(model, alphabet, sw);
            System.out.println(sw);
        } catch (IOException e) {
            //
        }

        statistics.totalLearnTime = timeElapsed/CONVERT_TO_MS;

        statistics.minNextResponseTime /= CONVERT_TO_MS;
        statistics.maxNextResponseTime /= CONVERT_TO_MS;
        statistics.averageNextResponseTime /= statistics.totalSentQueries;
        statistics.averageNextResponseTime /= CONVERT_TO_MS;

        statistics.minProcessingTime /= CONVERT_TO_MS;
        statistics.maxProcessingTime /= CONVERT_TO_MS;
        statistics.averageProcessingTime /= statistics.totalSentQueries;
        statistics.averageProcessingTime /= CONVERT_TO_MS;

        statistics.minBatchProcessingTime /= CONVERT_TO_MS;
        statistics.maxBatchProcessingTime /= CONVERT_TO_MS;
        statistics.averageBatchProcessingTime /= statistics.totalBatches;
        statistics.averageBatchProcessingTime /= CONVERT_TO_MS;

        statistics.minStartUpTime /= CONVERT_TO_MS;
        statistics.maxStartUpTime /= CONVERT_TO_MS;
        statistics.averageStartUpTime /= statistics.totalBatches;
        statistics.averageStartUpTime /= CONVERT_TO_MS;

        System.out.println(statistics);
    }
}

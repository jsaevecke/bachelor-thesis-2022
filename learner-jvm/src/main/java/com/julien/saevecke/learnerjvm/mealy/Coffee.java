package com.julien.saevecke.learnerjvm.mealy;

import com.julien.saevecke.learnerjvm.membership.RabbitMQOracle;
import com.julien.saevecke.learnerjvm.statistics.Statistics;
import de.learnlib.algorithms.dhc.mealy.MealyDHC;
import de.learnlib.api.query.DefaultQuery;
import de.learnlib.filter.cache.mealy.MealyCacheOracle;
import de.learnlib.oracle.equivalence.SimulatorEQOracle;
import net.automatalib.serialization.dot.GraphDOT;
import net.automatalib.words.Word;
import net.automatalib.words.impl.Alphabets;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Coffee {
    public static final String POD = "POD";
    public static final String CLEAN = "CLEAN";
    public static final String WATER = "WATER";
    public static final String BUTTON = "BUTTON";

    @Autowired
    private RabbitMQOracle membershipOracle;
    @Autowired
    private Statistics statistics;

    @Value("${SAVE_RESULTS_TO_FILE:true}")
    private boolean saveResultsToFile = true;

    @Value("${FILENAME_PREFIX:run_}")
    private String fileNamePrefix = "run_";

    public void learn() {
        var alphabet = Alphabets.fromArray(POD, CLEAN, WATER, BUTTON);
        var cacheOracle = MealyCacheOracle.createDAGCacheOracle(alphabet, membershipOracle);
        var learner = new MealyDHC<>(alphabet, cacheOracle);
        var eq = new SimulatorEQOracle<>(new CoffeeConcrete().coffeeMachine());

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
            if(saveResultsToFile) {
                String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
                try (PrintWriter out = new PrintWriter( fileNamePrefix + fileName + "_digraph" + ".txt")) {
                    out.println(sw);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println(sw);
            }
        } catch (IOException e) {
            //
        }

        statistics.totalLearnTime = timeElapsed;

        statistics.averageNextResponseTime /= statistics.totalSentQueries;
        statistics.averageProcessingTime /= statistics.totalSentQueries;
        statistics.averageStartUpTime /= statistics.totalSentQueries;
        statistics.averageBatchProcessingTime /= statistics.totalBatches;
        statistics.averageBatchSize /= statistics.totalBatches;

        if(saveResultsToFile) {
            String fileName = new SimpleDateFormat("yyyyMMddHHmm").format(new Date());
            try (PrintWriter out = new PrintWriter(fileNamePrefix + fileName + ".txt")) {
                out.println(statistics);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        } else {
            System.out.println(statistics);
        }
    }
}

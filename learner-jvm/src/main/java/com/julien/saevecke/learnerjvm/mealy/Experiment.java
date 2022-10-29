package com.julien.saevecke.learnerjvm.mealy;

import com.google.common.base.CaseFormat;
import com.julien.saevecke.learnerjvm.membership.RabbitMQOracle;
import com.julien.saevecke.learnerjvm.statistics.Statistics;
import de.learnlib.algorithms.dhc.mealy.MealyDHC;
import de.learnlib.api.query.DefaultQuery;
import de.learnlib.filter.cache.mealy.MealyCacheOracle;
import de.learnlib.oracle.equivalence.SimulatorEQOracle;
import net.automatalib.serialization.dot.GraphDOT;
import net.automatalib.words.Word;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class Experiment {

    @Autowired
    private ApplicationContext appContext;
    @Autowired
    private RabbitMQOracle membershipOracle;
    @Autowired
    private Statistics statistics;

    @Value("${SAVE_RESULTS_TO_FILE:true}")
    private boolean saveResultsToFile = true;

    @Value("${FILENAME_PREFIX:PREFIX}")
    private String fileNamePrefix = "";

    @Value("${STRATEGY_NAME:UNK_STRAT}")
    private String strategyName = "";

    @Value("${MEALY_MACHINE_TYPE:coffee}")
    private String mealyMachineType = "coffee";

    @Value("${SAVE_COMPLETED_HYPOTHESIS_MODEL:false}")
    private boolean saveCompletedHypothesisModel = false;

    public void learn() throws IOException {
        String filePathPrefix = "./resources/mealy-machines/";
        var mealyMachine = new MealyMachine(new File(filePathPrefix + mealyMachineType + ".dot"));

        var alphabet = mealyMachine.getAlphabet();
        var cacheOracle = MealyCacheOracle.createDAGCacheOracle(alphabet, membershipOracle);
        var learner = new MealyDHC<>(alphabet, cacheOracle);
        var eq = new SimulatorEQOracle<>(mealyMachine.getModel());

        System.out.println("Type:" + mealyMachineType);
        System.out.println("Inputs:" + alphabet.size());
        System.out.println("States: " + mealyMachine.getModel().size());

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

        if(saveCompletedHypothesisModel){
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
                System.err.println("Can not save learned hypothesis model to file!");
            }
        }

        statistics.totalLearnTime = timeElapsed;

        statistics.averageNextResponseTime /= statistics.totalSentQueries;
        statistics.averageProcessingTime /= statistics.totalSentQueries;
        statistics.averageStartUpTime /= statistics.totalSentQueries;
        statistics.averageBatchProcessingTime /= statistics.totalBatches;
        statistics.averageBatchSize /= statistics.totalBatches;
        statistics.averageSequenceLength /= statistics.totalSentQueries;
        statistics.averageWaitTime /= statistics.totalSentQueries;

        var statisticResultsAsText = strategyName + statistics;
        if(saveResultsToFile) {
            var output = new BufferedWriter(new FileWriter(fileNamePrefix + ".txt", true));
            output.newLine();
            output.write(statisticResultsAsText);
            output.close();
        } else {
            System.out.println(statisticResultsAsText);
        }

        SpringApplication.exit(appContext, () -> 0);
        System.exit(0);
    }
}

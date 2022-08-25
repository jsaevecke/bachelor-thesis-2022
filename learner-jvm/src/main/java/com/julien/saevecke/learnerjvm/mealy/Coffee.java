package com.julien.saevecke.learnerjvm.mealy;

import com.julien.saevecke.learnerjvm.membership.RabbitMQOracle;
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

    @Autowired
    RabbitMQOracle membershipOracle;

    @EventListener(ApplicationReadyEvent.class)
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

        final var model = learner.getHypothesisModel();

        try {
            final var sw = new StringWriter();
            GraphDOT.write(model, alphabet, sw);
            System.out.println(sw);
        } catch (IOException e) {
            //
        }

        long finish = System.nanoTime();
        long timeElapsed = finish - start;

        System.out.println("Learn time: " + timeElapsed/1000000 + " ms");
    }
}

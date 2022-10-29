package com.julien.saevecke.learnerjvm.mealy;

import net.automatalib.automata.transducers.impl.compact.CompactMealy;
import net.automatalib.serialization.dot.DOTParsers;
import net.automatalib.words.Alphabet;

import java.io.File;
import java.io.IOException;

public class MealyMachine {
    private final CompactMealy<String, String> model;
    private final Alphabet<String> alphabet;

    public MealyMachine(File dotGraph) throws IOException {
        var graphModel = DOTParsers.mealy().readModel(dotGraph);

        this.alphabet = graphModel.alphabet;
        this.model = graphModel.model;
    }

    public CompactMealy<String, String> getModel() {
        return model;
    }

    public Alphabet<String> getAlphabet() {
        return alphabet;
    }
}

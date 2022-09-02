package com.julien.saevecke.learnerjvm.mealy;

import net.automatalib.automata.transducers.impl.compact.CompactMealy;
import net.automatalib.util.automata.builders.AutomatonBuilders;
import net.automatalib.words.impl.Alphabets;

public class CoffeeConcrete {
    public static final String OUT_OK = "ok";
    public static final String OUT_ERROR = "error";
    public static final String OUT_COFFEE = "coffee!";

    public static final String POD = "POD";
    public static final String CLEAN = "CLEAN";
    public static final String WATER = "WATER";
    public static final String BUTTON = "BUTTON";
    
    public CompactMealy<String, String> coffeeMachine() {
        var alphabet = Alphabets.fromArray(POD, CLEAN, WATER, BUTTON);
        var automaton = new CompactMealy<String, String>(alphabet);

        // TODO: put states and output in separate classes
        // @formatter:off
        return AutomatonBuilders.forMealy(automaton)
                .withInitial("a")
                .from("a")
                .on(WATER).withOutput(OUT_OK).to("c")
                .on(POD).withOutput(OUT_OK).to("b")
                .on(BUTTON).withOutput(OUT_ERROR).to("f")
                .on(CLEAN).withOutput(OUT_OK).loop()
                .from("b")
                .on(WATER).withOutput(OUT_OK).to("d")
                .on(POD).withOutput(OUT_OK).loop()
                .on(BUTTON).withOutput(OUT_ERROR).to("f")
                .on(CLEAN).withOutput(OUT_OK).to("a")
                .from("c")
                .on(WATER).withOutput(OUT_OK).loop()
                .on(POD).withOutput(OUT_OK).to("d")
                .on(BUTTON).withOutput(OUT_ERROR).to("f")
                .on(CLEAN).withOutput(OUT_OK).to("a")
                .from("d")
                .on(WATER, POD).withOutput(OUT_OK).loop()
                .on(BUTTON).withOutput(OUT_COFFEE).to("e")
                .on(CLEAN).withOutput(OUT_OK).to("a")
                .from("e")
                .on(WATER, POD, BUTTON).withOutput(OUT_ERROR).to("f")
                .on(CLEAN).withOutput(OUT_OK).to("a")
                .from("f")
                .on(WATER, POD, BUTTON, CLEAN).withOutput(OUT_ERROR).loop()
                .create();
        // @formatter:on
    }
}

package codegenerator;

import codegenerator.states.*;
import scanner.token.Token;

import java.util.HashMap;
import java.util.Map;

public class Context {
    private final CodeGenerator codeGenerator;
    private Map<Integer, State> states;


    public Context(CodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
        this.states = new HashMap<>();
        states.put(0, new Zero(this));
        states.put(1, new One(this));
        states.put(2, new Two(this));
        states.put(3, new Three(this));
        states.put(4, new Four(this));
        states.put(5, new Five(this));
        states.put(6, new Six(this));
        states.put(7, new Seven(this));
        states.put(8, new Eight(this));
        states.put(9, new Nine(this));
        states.put(10, new Ten(this));
        states.put(11, new Eleven(this));
        states.put(12, new Twelve(this));
        states.put(13, new Thirteen(this));
        states.put(14, new Fourteen(this));
        states.put(15, new Fifteen(this));
        states.put(16, new Sixteen(this));
        states.put(17, new Seventeen(this));
        states.put(18, new Eighteen(this));
        states.put(19, new Nineteen(this));
        states.put(20, new Twenty(this));
        states.put(21, new TwentyOne(this));
        states.put(22, new TwentyTwo(this));
        states.put(23, new TwentyThree(this));
        states.put(24, new TwentyFour(this));
        states.put(25, new TwentyFive(this));
        states.put(26, new TwentySix(this));
        states.put(27, new TwentySeven(this));
        states.put(28, new TwentyEight(this));
        states.put(29, new TwentyNine(this));
        states.put(30, new Thirty(this));
        states.put(31, new ThirtyOne(this));
        states.put(32, new ThirtyTwo(this));
        states.put(33, new ThirtyThree(this));
    }

    public void handle(Integer func, Token token) {
        this.states.get(func).handle(token);
    }

    public CodeGenerator getCodeGenerator() {
        return codeGenerator;
    }
}

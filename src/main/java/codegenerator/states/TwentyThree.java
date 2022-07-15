package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class TwentyThree extends State {

    public TwentyThree(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().defClass();
    }

    @Override
    public State getNextState() {
        return new TwentyFour(getContext());
    }
}

package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class TwentyEight extends State {

    public TwentyEight(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().defVar();
    }

    @Override
    public State getNextState() {
        return new TwentyNine(getContext());
    }
}


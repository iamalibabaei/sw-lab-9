package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Thirty extends State {

    public Thirty(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().defParam();
    }

    @Override
    public State getNextState() {
        return new ThirtyOne(getContext());
    }
}

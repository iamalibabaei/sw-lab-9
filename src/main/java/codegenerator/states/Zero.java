package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Zero extends State {

    public Zero(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {

    }

    @Override
    public State getNextState() {
        return new One(getContext());
    }
}

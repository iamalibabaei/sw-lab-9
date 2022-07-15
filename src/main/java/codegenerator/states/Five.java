package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Five extends State {

    public Five(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().intPid(next);
    }

    @Override
    public State getNextState() {
        return new Six(getContext());
    }
}

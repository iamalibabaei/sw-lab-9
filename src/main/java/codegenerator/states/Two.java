package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Two extends State {

    public Two(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().pid(next);
    }

    @Override
    public State getNextState() {
        return new Three(getContext());
    }
}

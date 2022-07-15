package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Four extends State {
    
    public Four(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().kPid(next);
    }

    @Override
    public State getNextState() {
        return new Five(getContext());
    }
}

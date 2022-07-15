package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Seven extends State {

    public Seven(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().call();
    }

    @Override
    public State getNextState() {
        return new Eight(getContext());
    }
}

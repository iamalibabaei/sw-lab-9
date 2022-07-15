package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class One extends State {

    public One(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().checkID();
    }

    @Override
    public State getNextState() {
        return new Two(getContext());
    }
}

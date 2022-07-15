package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Thirteen extends State {
    public Thirteen(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().label();
    }

    @Override
    public State getNextState() {
        return new Fourteen(getContext());
    }
}

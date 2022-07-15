package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Fourteen extends State {

    public Fourteen(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().save();
    }

    @Override
    public State getNextState() {
        return new Fifteen(getContext());
    }
}

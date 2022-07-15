package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class TwentySix extends State {

    public TwentySix(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().extend();
    }

    @Override
    public State getNextState() {
        return new TwentySeven(getContext());
    }
}

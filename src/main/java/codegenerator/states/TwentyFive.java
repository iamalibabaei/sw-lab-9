package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class TwentyFive extends State {

    public TwentyFive(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().popClass();
    }

    @Override
    public State getNextState() {
        return new TwentySix(getContext());
    }
}

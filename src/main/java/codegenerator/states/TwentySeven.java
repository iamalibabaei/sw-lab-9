package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class TwentySeven extends State {

    public TwentySeven(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().defField();
    }

    @Override
    public State getNextState() {
        return new TwentyEight(getContext());
    }
}

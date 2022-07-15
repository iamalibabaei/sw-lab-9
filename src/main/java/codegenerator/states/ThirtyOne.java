package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class ThirtyOne extends State {

    public ThirtyOne(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().lastTypeBool();
    }

    @Override
    public State getNextState() {
        return new ThirtyTwo(getContext());
    }
}

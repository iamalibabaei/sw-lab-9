package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class ThirtyTwo extends State {

    public ThirtyTwo(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().lastTypeInt();
    }

    @Override
    public State getNextState() {
        return new ThirtyThree(getContext());
    }
}

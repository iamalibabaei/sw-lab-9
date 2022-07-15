package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class TwentyFour extends State {
    public TwentyFour(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().defMethod();
    }

    @Override
    public State getNextState() {
        return new TwentyFive(getContext());
    }
}

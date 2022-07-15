package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class ThirtyThree extends State {

    public ThirtyThree(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().defMain(this.getContext().getCodeGenerator().getAddressStackLastElement().getNum());

    }

    @Override
    public State getNextState() {
        return null;
    }
}

package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class TwentyNine extends State {

    public TwentyNine(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().methodReturn(this.getContext().getCodeGenerator().getAddressStackLastElement());
    }

    @Override
    public State getNextState() {
        return new Thirty(getContext());
    }
}

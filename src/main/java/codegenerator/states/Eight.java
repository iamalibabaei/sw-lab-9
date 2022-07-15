package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Eight extends State {
    public Eight(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().arg(this.getContext().getCodeGenerator().getAddressStackLastElement());

    }

    @Override
    public State getNextState() {
        return new Nine(getContext());
    }
}

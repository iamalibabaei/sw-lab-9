package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Seventeen extends State {

    public Seventeen(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().jpHere(this.getContext().getCodeGenerator().getAddressStackLastElement());
    }

    @Override
    public State getNextState() {
        return new Eighteen(getContext());
    }
}

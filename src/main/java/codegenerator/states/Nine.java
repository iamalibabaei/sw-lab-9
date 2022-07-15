package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Nine extends State {

    public Nine(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().assign(this.getContext().getCodeGenerator().getAddressStackLastElement(), this.getContext().getCodeGenerator().getAddressStackLastElement());
    }

    @Override
    public State getNextState() {
        return new Ten(getContext());
    }
}

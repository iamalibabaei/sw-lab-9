package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Eighteen extends State {

    public Eighteen(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().print(this.getContext().getCodeGenerator().getAddressStackLastElement());
    }

    @Override
    public State getNextState() {
        return new Nineteen(getContext());
    }
}

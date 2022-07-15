package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Twelve extends State {
    public Twelve(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().pushToAddressStack(
                this.getContext().getCodeGenerator().mult(
                        this.getContext().getCodeGenerator().
                                getAddressStackLastElement(), this.getContext().getCodeGenerator().getAddressStackLastElement()
                ));

    }

    @Override
    public State getNextState() {
        return new Thirteen(getContext());
    }
}

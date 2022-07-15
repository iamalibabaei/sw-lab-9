package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Sixteen extends State {

    public Sixteen(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().pushToAddressStack(
                this.getContext().getCodeGenerator().jpfSave(
                        this.getContext().getCodeGenerator().getAddressStackLastElement(),
                        this.getContext().getCodeGenerator().getAddressStackLastElement())
        );

    }

    @Override
    public State getNextState() {
        return new Seventeen(getContext());
    }
}

package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Fifteen extends State {

    public Fifteen(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().whileDef(
                this.getContext().getCodeGenerator().getAddressStackLastElement(),
                this.getContext().getCodeGenerator().getAddressStackLastElement(),
                this.getContext().getCodeGenerator().getAddressStackLastElement()
        );

    }

    @Override
    public State getNextState() {
        return new Sixteen(getContext());
    }
}

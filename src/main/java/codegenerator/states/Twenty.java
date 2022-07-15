package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Twenty extends State {

    public Twenty(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().pushToAddressStack(
                this.getContext().getCodeGenerator().lessThan(
                        this.getContext().getCodeGenerator().getAddressStackLastElement(),
                        this.getContext().getCodeGenerator().getAddressStackLastElement())
        );

    }
    @Override
    public State getNextState() {
        return new TwentyOne(getContext());
    }

}

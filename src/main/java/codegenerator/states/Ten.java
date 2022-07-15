package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Ten extends State {

    public Ten(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().pushToAddressStack(
                this.getContext().getCodeGenerator().add(
                        this.getContext().getCodeGenerator().getAddressStackLastElement(),
                        this.getContext().getCodeGenerator().getAddressStackLastElement())
        );
    }

    @Override
    public State getNextState() {
        return new Eleven(getContext());
    }
}

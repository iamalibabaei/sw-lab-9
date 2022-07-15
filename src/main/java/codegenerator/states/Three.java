package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Three extends State {

    public Three(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().getAddressStackLastElement();
        this.getContext().getCodeGenerator().getAddressStackLastElement();
        this.getContext().getCodeGenerator().pushToAddressStack(this.getContext().getCodeGenerator().fPid());
    }

    @Override
    public State getNextState() {
        return new Four(getContext());
    }
}

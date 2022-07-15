package codegenerator.states;

import codegenerator.Context;
import codegenerator.State;
import scanner.token.Token;

public class Six extends State {

    public Six(Context context) {
        super(context);
    }

    @Override
    public void handle(Token next) {
        this.getContext().getCodeGenerator().getAddressStackLastElement();
        this.getContext().getCodeGenerator().getAddressStackLastElement();
        this.getContext().getCodeGenerator().startCall();
    }

    @Override
    public State getNextState() {
        return new Seven(getContext());
    }

}

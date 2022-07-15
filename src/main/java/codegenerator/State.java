package codegenerator;

import scanner.token.Token;

public abstract class State {
    private final Context context;

    protected State(Context context) {
        this.context = context;
    }

    public abstract void handle(Token next);

    public abstract State getNextState();

    public Context getContext() {
        return context;
    }
}
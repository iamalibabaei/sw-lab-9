package parser;

public class Action {
  private final act action;
  private final int number;

  public act getAction() {
    return action;
  }

  public int getNumber() {
    return number;
  }

  public Action(act action, int number) {
    this.action = action;
    this.number = number;
  }

  public String toString() {
    switch (action) {
      case ACCEPT:
        return "acc";
      case SHIFT:
        return "s" + number;
      case REDUCE:
        return "r" + number;
    }
    return action.toString() + number;
  }
}

enum act {
  SHIFT,
  REDUCE,
  ACCEPT
}

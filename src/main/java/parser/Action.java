package parser;

public class Action {
  private final Act action;
  private final int number;

  public Act getAction() {
    return action;
  }

  public int getNumber() {
    return number;
  }

  public Action(Act action, int number) {
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

enum Act {
  SHIFT,
  REDUCE,
  ACCEPT
}

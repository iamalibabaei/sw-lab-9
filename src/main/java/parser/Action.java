package parser;

public class Action {
  private act action;
  //if action = shift : number is state
  //if action = reduce : number is number of rule
  private int number;

  public act getAction() {
    return action;
  }

  public void setAction(act action) {
    this.action = action;
  }

  public int getNumber() {
    return number;
  }

  public void setNumber(int number) {
    this.number = number;
  }

  public Action(act action, int number) {
    this.action = action;
    this.number = number;
  }

  public String toString() {
    switch (action) {
      case accept:
        return "acc";
      case shift:
        return "s" + number;
      case reduce:
        return "r" + number;
    }
    return action.toString() + number;
  }
}

enum act {
  shift,
  reduce,
  accept
}

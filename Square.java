public class Square {
  private boolean filled;

  public Square() {
    filled = false;
  }

  public Square(boolean f) {
    filled = f;
  }

  public boolean isFilled() {
    return filled;
  }

  public String getFilled(String type) {
    switch(type) {
      case "int":
        if (filled)
          return "1";
        else
          return "0";

      case "bool":
        return "" + filled;

      case "char":
        if (filled)
          return "y";
        else
          return "n";

      case "str":
        if (filled)
          return "O";
        else
          return "X";
    }

    return "-1";
  }

  public void setFilled(boolean f) {
    filled = f;
  }

  public String toString() {
    if (filled)
      return "O";
    else
      return " ";
  }
}


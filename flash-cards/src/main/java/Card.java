import java.util.List;

class Card {
  public String input;
  public String output;

  public Card() {

  }

  public Card(String input, String output) {
    this.input = input;
    this.output = output;
  }

  public List<Solution> getSolutions() {
    return null;
  }

  @Override
  public String toString() {
    return "Card{" +
        "input='" + input + '\'' +
        ", output='" + output + '\'' +
        '}';
  }
}

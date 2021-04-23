import java.util.List;
import java.util.Objects;

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

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Card card = (Card) o;
    return Objects.equals(input, card.input) && Objects
        .equals(output, card.output);
  }

  @Override
  public int hashCode() {
    return Objects.hash(input, output);
  }
}

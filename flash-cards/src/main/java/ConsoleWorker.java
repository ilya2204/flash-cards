import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;

// знаем, что код плохой, исправим на нормальный)) (торопились выкатить релиз)
public class ConsoleWorker implements Callable<Integer> {

  CardLibrary cardLibrary;

  ConsoleWorker(CardLibrary cardLibrary) {
    this.cardLibrary = cardLibrary;
  }

  Scanner in = new Scanner(System.in);

  @Override
  public Integer call() {
    System.out.println("Waiting for commands");
    String s;
    while (in.hasNextLine()) {
      s = in.nextLine();
      if (s.equals("add card")) {
        addCard();
      } else if (s.equals("get all cards")) {
        getAllCards();
      } else {
        System.out.println("Unknown command: \"" + s + "\"");
      }
    }
    return 1;
  }

  private void addCard() {
    String input, output;
    System.out.println("Enter card input");
    input = in.nextLine();
    System.out.println("Enter card output");
    output = in.nextLine();

    Card card = this.cardLibrary.create(input, output);

    System.out.println("Card created: " + card);

  }

  private void getAllCards() {
    Map<Integer, Card> allCards = cardLibrary.getActiveCards();

    for (var entry : allCards.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }

  public static void main(String[] args) {
    CardLibrary cardLibrary = new InMemoryCardLibrary();
    ConsoleWorker consoleWorker = new ConsoleWorker(cardLibrary);
    consoleWorker.call();
  }
}
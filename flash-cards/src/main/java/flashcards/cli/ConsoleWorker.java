package flashcards.cli;

import flashcards.models.card.Card;
import flashcards.models.card.CardLibrary;
import flashcards.workers.CardPicker;
import flashcards.models.card.InMemoryCardLibrary;
import flashcards.models.solution.InMemorySolutionLibrary;
import flashcards.models.solution.SolutionLibrary;
import flashcards.workers.InputHandler;
import flashcards.workers.IsOkReportGenerator;
import flashcards.workers.MostMistakeCardPicker;

import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;


public class ConsoleWorker implements Callable<Integer> {
  final CardLibrary cardLibrary;
  final CardPicker picker;
  final InputHandler handler;
  public static Integer trainNumber = 0;

  ConsoleWorker(CardLibrary cardLibrary, CardPicker picker, InputHandler handler) {
    this.cardLibrary = cardLibrary;
    this.picker = picker;
    this.handler = handler;
  }

  Scanner in = new Scanner(System.in);

  @Override
  public Integer call() {
    System.out.println("Waiting for commands");
    String s;
    while (in.hasNextLine()) {
      s = in.nextLine();
      switch (s.toLowerCase(Locale.ROOT)) {
        case "add card" -> {
          addCard();
        }
        case "get all cards" -> {
          getAllCards();
        }
        case "start training" -> {
          startTraining();
        }
        default -> {
          System.out.println("Unknown command: \"" + s + "\"");
        }
      }
    }
    return 1;
  }

  private void startTraining() {
    Card card = picker.getCard();
    System.out.println("flashcards.Card: " + card.input);

    while (in.hasNextLine()) {
      String userInput = in.nextLine();
      String report = handler.handleInput(card, userInput);
      if (report == null) {
        System.out.println("You enter correct solution!");
        break;
      }
      System.out.println(report);
      System.out.println("Try again");
    }
  }

  private void getCard() {


  }

  private void addCard() {
    String input, output;
    System.out.println("Enter card input");
    input = in.nextLine().toLowerCase(Locale.ROOT).trim();
    System.out.println("Enter card output");
    output = in.nextLine().toLowerCase(Locale.ROOT).trim();

    Card card = this.cardLibrary.get(input);
    if (card != null) {
      System.out.println("flashcards.Card already exists: " + card);
      return;
    }

    card = this.cardLibrary.create(input, output);

    System.out.println("flashcards.Card created: " + card);

  }

  private void getAllCards() {
    Map<Integer, Card> allCards = cardLibrary.getActiveCards();

    for (var entry : allCards.entrySet()) {
      System.out.println(entry.getKey() + ": " + entry.getValue());
    }
  }

  public static void main(String[] args) {
    CardLibrary cardLibrary = new InMemoryCardLibrary();
    SolutionLibrary solutionLibrary = new InMemorySolutionLibrary();
    InputHandler handler = new InputHandler(new IsOkReportGenerator(), solutionLibrary);
    CardPicker picker = new MostMistakeCardPicker(cardLibrary, solutionLibrary);
    ConsoleWorker consoleWorker = new ConsoleWorker(cardLibrary, picker, handler);
    consoleWorker.call();
  }
}

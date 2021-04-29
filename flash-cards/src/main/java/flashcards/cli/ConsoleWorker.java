package flashcards.cli;

import flashcards.models.card.Card;
import flashcards.models.card.CardLibrary;
import flashcards.workers.*;
import flashcards.models.card.InMemoryCardLibrary;
import flashcards.models.solution.InMemorySolutionLibrary;
import flashcards.models.solution.SolutionLibrary;

import java.util.Locale;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.Callable;


public class ConsoleWorker implements Callable<Integer> {
  final CardLibrary cardLibrary;
  CardPicker picker;
  final InputHandler handler;
  public static Integer trainNumber = 17;

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
        case "switch picker" -> {
          switchPicker();
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
      System.out.println("Enter next command");
    }
    return 1;
  }

  private void switchPicker() {
    System.out.println("Options:");
    int index = 1;
    if (picker instanceof MostMistakeCardPicker) {
      index++;
    }
    System.out.println("1. SpacedRepetitionCardPicker " + (index == 1 ? "(picked)" : ""));
    System.out.println("2. MostMistakeCardPicker " + (index == 2 ? "(picked)" : ""));
    System.out.println("Enter picker number");
    String number = in.nextLine();
    if (number.equals("1") || number.equals("1.")) {
      if (index == 1) {
        System.out.println("This option is picked already");
      } else {
        picker = new SpacedRepetitionCardPicker(cardLibrary, handler.library);
        System.out.println("Picker changed successful");
      }
      return;
    }
    if (number.equals("2") || number.equals("2.")) {
      if (index == 2) {
        System.out.println("This option is picked already");
      } else {
        picker = new MostMistakeCardPicker(cardLibrary, handler.library);
        System.out.println("Picker changed successful");
      }
      return;
    }
    System.out.println("You enter invalid number");
  }

  private void startTraining() {
    trainNumber++;
    Card card = picker.getCard();
    if (card == null) {
      System.out.println("No cards for this training. You can start new training or create new card");
      return;
    }

    for (int i = 0; i < cardLibrary.getActiveCards().size(); i++) {
      if (card == null) {
        break;
      }
      System.out.println(card);
      System.out.println("Enter solution:");
      String userInput = in.nextLine();
      String report = handler.handleInput(card, userInput);
      picker.postAnalyzingCallback(card);
      if (report == null) {
        System.out.println("You enter correct solution!");
        break;
      }
      System.out.println(report);
      System.out.println("Showing next card");

      card = picker.getCard();
    }
    System.out.println("Cards finished! You can start new training");
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
    CardPicker picker = new SpacedRepetitionCardPicker(cardLibrary, solutionLibrary);
    ConsoleWorker consoleWorker = new ConsoleWorker(cardLibrary, picker, handler);
    consoleWorker.call();
  }
}

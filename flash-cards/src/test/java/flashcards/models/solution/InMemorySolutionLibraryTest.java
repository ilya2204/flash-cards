package flashcards.models.solution;

import static org.junit.jupiter.api.Assertions.*;

import flashcards.cli.ConsoleWorker;
import flashcards.models.card.Card;
import flashcards.models.solution.InMemorySolutionLibrary;
import org.junit.jupiter.api.Test;

class InMemorySolutionLibraryTest {

  @Test
  void mainTest() {
    ConsoleWorker.trainNumber = 1;
    InMemorySolutionLibrary solutionLibrary = new InMemorySolutionLibrary();
    Card card1 = new Card("test card", "for library");
    Card card2 = new Card("second test card", "test for library");

    assertEquals(0, solutionLibrary.getAllSolutions().count());

    solutionLibrary.create("first try", card1);

    assertEquals(1, solutionLibrary.getAllSolutions().count());

    solutionLibrary.create("for second try", card1);
    solutionLibrary.create("ez solution", card2);

    assertEquals(3, solutionLibrary.getAllSolutions().count());

    assertEquals(2, solutionLibrary.getCardSolutions(card1).count());
    assertEquals(1, solutionLibrary.getCardSolutions(card2).count());


    assertEquals(1, solutionLibrary.getLastSolutionTrainNumber(card1));

    ConsoleWorker.trainNumber = 322;

    solutionLibrary.create("next train", card1);
    assertEquals(322, solutionLibrary.getLastSolutionTrainNumber(card1));

  }
}

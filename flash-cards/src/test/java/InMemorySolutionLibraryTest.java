import static org.junit.jupiter.api.Assertions.*;

import flashcards.models.card.Card;
import flashcards.models.solution.InMemorySolutionLibrary;
import org.junit.jupiter.api.Test;

class InMemorySolutionLibraryTest {

  @Test
  void solutionTest() {
    InMemorySolutionLibrary solutionLibrary = new InMemorySolutionLibrary();
    Card card1 = new Card("test card", "for library");
    Card card2 = new Card("second test card", "test for library");

    solutionLibrary.create("first try", card1);
    solutionLibrary.create("for second try", card1);
    solutionLibrary.create("ez solution", card2);

    assertEquals(solutionLibrary.getCardSolutions(card1).size(), 2);
    assertEquals(solutionLibrary.getCardSolutions(card2).size(), 1);
  }
}

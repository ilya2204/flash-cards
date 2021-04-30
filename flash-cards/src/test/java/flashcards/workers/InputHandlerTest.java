package flashcards.workers;

import static org.junit.jupiter.api.Assertions.*;

import flashcards.models.card.Card;
import flashcards.models.solution.InMemorySolutionLibrary;
import flashcards.models.solution.SolutionLibrary;
import org.junit.jupiter.api.Test;

class InputHandlerTest {

  @Test
  void handleInput() {
    SolutionLibrary solutionLibrary = new InMemorySolutionLibrary();
    SolutionReportGenerator solutionReportGenerator = new IsOkReportGenerator();

    InputHandler inputHandler = new InputHandler(solutionReportGenerator, solutionLibrary);

    Card card1 = new Card("in", "out");

    String trueRes = "out";

    String report = solutionReportGenerator.generateReport(card1, trueRes);

    assertEquals(report, inputHandler.handleInput(card1, "out"));


  }
}
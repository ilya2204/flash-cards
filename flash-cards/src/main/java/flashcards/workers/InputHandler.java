package flashcards.workers;

import flashcards.models.card.Card;
import flashcards.models.solution.SolutionLibrary;

public class InputHandler {
  final private SolutionReportGenerator reportGenerator;
  final private SolutionLibrary library;

  public InputHandler(SolutionReportGenerator reportGenerator, SolutionLibrary library) {
    this.reportGenerator = reportGenerator;
    this.library = library;
  }

  public String handleInput(Card card, String input) {
    String report = reportGenerator.generateReport(card, input);
    System.out.println("creating handleInput");
    library.create(input, card);
    return report;
  }
}

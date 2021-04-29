import static org.junit.jupiter.api.Assertions.*;

import flashcards.models.card.Card;
import flashcards.workers.IsOkReportGenerator;
import org.junit.jupiter.api.Test;

class IsOkReportGeneratorTest {

  @Test
  void generateReport() {
    IsOkReportGenerator isOkReportGenerator = new IsOkReportGenerator();
    Card card = new Card("test input", "test output");

    assertNull(isOkReportGenerator.generateReport(card, "test output"));
    assertEquals(isOkReportGenerator.generateReport(card, "wrong input"), "Wrong solution");
  }
}

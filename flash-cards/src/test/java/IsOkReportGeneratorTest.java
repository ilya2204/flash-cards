import static org.junit.jupiter.api.Assertions.*;

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
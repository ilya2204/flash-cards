public class IsOkReportGenerator implements SolutionReportGenerator {
  public String generateReport(Card card, String input) {
    if (card.output.contentEquals(input)) {
      return null;
    } else {
      return "Wrong solution";
    }
  }
}

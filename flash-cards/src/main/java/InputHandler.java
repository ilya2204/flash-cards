class InputHandler {
  final private SolutionReportGenerator reportGenerator;
  final private SolutionLibrary library;

  InputHandler(SolutionReportGenerator reportGenerator, SolutionLibrary library) {
    this.reportGenerator = reportGenerator;
    this.library = library;
  }

  public String handleInput(Card card, String input) {
    String report = reportGenerator.generateReport(card, input);
    library.create(input, card);
    return report;
  }
}

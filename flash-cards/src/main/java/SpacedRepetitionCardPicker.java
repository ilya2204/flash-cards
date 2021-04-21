public class SpacedRepetitionCardPicker implements CardPicker {

  final CardLibrary cardLibrary;
  final SolutionLibrary solutionLibrary;

  public SpacedRepetitionCardPicker(CardLibrary cardLibrary, SolutionLibrary solutionLibrary) {
    this.cardLibrary = cardLibrary;
    this.solutionLibrary = solutionLibrary;
  }

  public SpacedRepetitionCardPicker(SolutionLibrary solutionLibrary) {
    this.cardLibrary = new InMemoryCardLibrary();
    this.solutionLibrary = solutionLibrary;
  }

  public SpacedRepetitionCardPicker(CardLibrary cardLibrary) {
    this.cardLibrary = cardLibrary;
    this.solutionLibrary = new InMemorySolutionLibrary();
  }

  public SpacedRepetitionCardPicker() {
    this.solutionLibrary = new InMemorySolutionLibrary();
    this.cardLibrary = new InMemoryCardLibrary();
  }


  @Override
  public Card getCard() {
    return cardLibrary.get(0);
  }
}

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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
    if (cardLibrary.getActiveCards().size() == 0) {
      return null;
    }

    List<Solution> allSolutions = solutionLibrary.getAllSolutions();
    Map<Card, Float> mistakesCount = new HashMap<>();
    Collection<Card> cards = cardLibrary.getActiveCards().values();

    cards.forEach(card -> {
      List<Solution> solution = solutionLibrary.getCardSolutions(card);
      Float mistakesCnt = (float) solution.stream()
          .filter(solution1 -> !solution1.result.equals(card.output)).count() / solution.size();
      mistakesCount.put(card, mistakesCnt);
    });

    System.out.println("mistakesCount: " + mistakesCount);
    Comparator<? super java.util.Map.Entry<Card, Float>> comparator = Entry.comparingByValue();

    Card card = mistakesCount.entrySet().stream().max(comparator).get().getKey();

    if (allSolutions.size() > 0) {
      if (allSolutions.get(allSolutions.size() - 1).card.equals(card)) {
        return cardLibrary.getRandom();
      }
    }

    return card;
  }
}

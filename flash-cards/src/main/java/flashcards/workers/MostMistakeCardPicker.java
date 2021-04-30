package flashcards.workers;

import flashcards.models.card.Card;
import flashcards.models.card.CardLibrary;
import flashcards.models.card.InMemoryCardLibrary;
import flashcards.models.solution.InMemorySolutionLibrary;
import flashcards.models.solution.Solution;
import flashcards.models.solution.SolutionLibrary;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MostMistakeCardPicker implements CardPicker {

  final CardLibrary cardLibrary;
  final SolutionLibrary solutionLibrary;

  public MostMistakeCardPicker(CardLibrary cardLibrary, SolutionLibrary solutionLibrary) {
    this.cardLibrary = cardLibrary;
    this.solutionLibrary = solutionLibrary;
  }

  public MostMistakeCardPicker(SolutionLibrary solutionLibrary) {
    this.cardLibrary = new InMemoryCardLibrary();
    this.solutionLibrary = solutionLibrary;
  }

  public MostMistakeCardPicker(CardLibrary cardLibrary) {
    this.cardLibrary = cardLibrary;
    this.solutionLibrary = new InMemorySolutionLibrary();
  }

  public MostMistakeCardPicker() {
    this.solutionLibrary = new InMemorySolutionLibrary();
    this.cardLibrary = new InMemoryCardLibrary();
  }


  @Override
  public Card getCard() {
    if (cardLibrary.getActiveCards().size() == 0) {
      return null;
    }

    Stream<Solution> allSolutions = solutionLibrary.getAllSolutions();
    Map<Card, Float> mistakesCount = new HashMap<>();
    Collection<Card> cards = cardLibrary.getActiveCards().values();

    cards.forEach(card -> {
      List<Solution> solution = solutionLibrary.getCardSolutions(card).collect(Collectors.toList());
      Float mistakesCnt = (float) solution.stream()
          .filter(solution1 -> !solution1.result.equals(card.output)).count() / solution.size();
      mistakesCount.put(card, mistakesCnt);
    });

    System.out.println("mistakesCount: " + mistakesCount);
    Comparator<? super Map.Entry<Card, Float>> comparator = Map.Entry.comparingByValue();

    Card card = mistakesCount.entrySet().stream().max(comparator).get().getKey();

    if (allSolutions.count() > 0) {

      if (allSolutions.collect(Collectors.toList()).get((int) (allSolutions.count() - 1)).card.equals(card)) {
        return cardLibrary.getRandom();
      }
    }

    return card;
  }

  @Override
  public void postAnalyzingCallback(Card card) {

  }
}

//package flashcards.workers;
//
//import flashcards.models.card.Card;
//import flashcards.models.card.CardPicker;
//
//public class SpacedRepetitionCardPicker implements CardPicker {
//  @Override
//  public Card getCard() {
//
//  }
//}

import flashcards.cli.ConsoleWorker;
import flashcards.models.card.Card;
import flashcards.models.card.CardLibrary;
import flashcards.models.solution.Solution;
import flashcards.models.solution.SolutionLibrary;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class SpacedRepetitionCardPicker implements CardPicker {

  Integer bucketsCnt = 3;
  Integer currentTraining = null;

  CardLibrary cardLibrary;
  SolutionLibrary solutionLibrary;
  Iterator<Card> cardIterator;

  public SpacedRepetitionCardPicker(CardLibrary cardLibrary, SolutionLibrary solutionLibrary) {
    this.cardLibrary = cardLibrary;
    this.solutionLibrary = solutionLibrary;
  }



  private Stream<Card> cardsForCurrentTraining() {
    return IntStream.rangeClosed(1, bucketsCnt)
        .boxed()
        .filter(n -> ConsoleWorker.trainNumber % n == 0)
        .flatMap(n -> cardLibrary.getCardsInBucket(n))
        .filter(
            card -> solutionLibrary.getLastSolutionTrainNumber(card) != ConsoleWorker.trainNumber);

  }

  @Override
  public Card getCard() {
    if (!currentTraining.equals(ConsoleWorker.trainNumber)) {
      currentTraining = ConsoleWorker.trainNumber;
      cardIterator = cardsForCurrentTraining().iterator();
    }
    return cardIterator.hasNext() ? cardIterator.next() : null;
  }

  @Override
  public void postAnalyzingCallback(Card card) {
    List<Solution> solutions = solutionLibrary.getCardSolutions(card).collect(Collectors.toList());
    Solution lastSolution = solutions.get(solutions.size() - 1);
    if (!lastSolution.card.output.equals(lastSolution.result)) {
      cardLibrary.moveToPrevBucket(card);
    }

    int bucket = cardLibrary.getBucket(card), rightInRow = 0;
    Solution solution;
    for (int idx = 0; idx < solutions.size(); idx++) {
      solution = solutions.get(solutions.size() - idx - 1);
      if (!solution.card.output.equals(solution.result)) {
        break;
      }
      rightInRow++;
    }

    if (rightInRow == bucket) {
//      if (b)
      cardLibrary.moveToNextBucket(card);
    }
  }

}

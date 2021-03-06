package flashcards.workers;

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
  Integer currentTraining = 0;

  CardLibrary cardLibrary;
  SolutionLibrary solutionLibrary;
  Iterator<Card> cardIterator;

  public SpacedRepetitionCardPicker(CardLibrary cardLibrary, SolutionLibrary solutionLibrary) {
    this.cardLibrary = cardLibrary;
    this.solutionLibrary = solutionLibrary;
  }



  public Stream<Card> cardsForCurrentTraining() {
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

    int rightInRow = 0;

    if (!lastSolution.card.output.equals(lastSolution.result)) {
      cardLibrary.moveToPrevBucket(card);
    } else {
      rightInRow++;
    }

    int currentBucket = cardLibrary.getBucket(card);
    int bucket = 1;
    int corAns = 0;
    for (Solution solution: solutions) {
      if (!solution.card.output.equals(solution.result)) {
        if (bucket != 1) {
          --bucket;
        }
        corAns = 0;
      } else {
        corAns++;
        if (corAns == bucket) {
          bucket++;
          corAns = 0;
        }
      }

    }

    if (bucket > currentBucket) {
      if (cardLibrary.moveToNextBucket(card) > bucketsCnt) {
        cardLibrary.delete(card.id);
      }
    }
  }

}

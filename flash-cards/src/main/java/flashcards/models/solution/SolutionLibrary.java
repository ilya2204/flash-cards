package flashcards.models.solution;

import flashcards.models.card.Card;

import java.util.List;
import java.util.stream.Stream;

public interface SolutionLibrary {
  Solution create(String input, Card card);
  Stream<Solution> getCardSolutions(Card card);
  Stream<Solution> getSessionCardSolutions(Card card);
  Stream<Solution> getAllSolutions();

  int getLastSolutionTrainNumber(Card card);
}

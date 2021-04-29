package flashcards.models.solution;

import flashcards.models.card.Card;

import java.util.List;

public interface SolutionLibrary {
  Solution create(String input, Card card);
  List<Solution> getCardSolutions(Card card);
  List<Solution> getSessionCardSolutions(Card card);
  List<Solution> getAllSolutions();
}

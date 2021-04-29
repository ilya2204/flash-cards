package flashcards.models.solution;


import flashcards.models.card.Card;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InMemorySolutionLibrary implements SolutionLibrary {

  final List<Solution> solutions;

  public InMemorySolutionLibrary() {
    solutions = new ArrayList<>();
  }

  @Override
  public Solution create(String input, Card card) {
    Solution solution = new Solution(card, input);
    solutions.add(solution);
    return solution;
  }

  @Override
  public Stream<Solution> getCardSolutions(Card card) {
    return solutions.stream().filter(solution -> solution.card.equals(card));
  }

  @Override
  public Stream<Solution> getSessionCardSolutions(Card card) {
    return getCardSolutions(card);
  }

  @Override
  public Stream<Solution> getAllSolutions() {
    return solutions.stream();
  }

  @Override
  public int getLastSolutionTrainNumber(Card card) {
    Optional<Solution> max = getCardSolutions(card).max(Comparator.comparing(a -> a.trainNumber));
    return max.isPresent() ? max.get().trainNumber : -1;
  }

}

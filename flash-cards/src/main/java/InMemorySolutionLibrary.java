import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
  public List<Solution> getCardSolutions(Card card) {
    return solutions.stream().filter(solution -> solution.card.equals(card)).collect(Collectors.toList());
  }

  @Override
  public List<Solution> getSessionCardSolutions(Card card) {
    return getCardSolutions(card);
  }

  @Override
  public List<Solution> getAllSolutions() {
    return solutions;
  }
}

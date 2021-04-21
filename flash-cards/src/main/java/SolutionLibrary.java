import java.util.List;

interface SolutionLibrary {
  Solution create(String input, Card card);
  List<Solution> getCardSolutions(Card card);
  List<Solution> getSessionCardSolutions(Card card);
}

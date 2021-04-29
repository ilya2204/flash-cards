package flashcards.models.card;

import java.util.Map;

public class DbCardLibrary implements CardLibrary {

  @Override
  public Card create(String input, String output) {
    return null;
  }

  @Override
  public Card update(Integer id, String input, String output) {
    return null;
  }

  @Override
  public void delete(Integer id) {

  }

  @Override
  public Card get(Integer id) {
    return null;
  }

  @Override
  public Card getRandom() {
    return null;
  }

  @Override
  public Card get(String input) {
    return null;
  }

  @Override
  public Map<Integer, Card> getActiveCards() {
    return null;
  }
}

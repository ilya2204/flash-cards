package flashcards.models.card;

import java.util.Map;
import java.util.stream.Stream;

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

  @Override
  public Integer getBucket(Card card) {
    return null;
  }

  @Override
  public Stream<Card> getCardsInBucket(Integer bucketNumber) {
    return null;
  }

  @Override
  public void moveToNextBucket(Card card) {

  }

  @Override
  public void moveToPrevBucket(Card card) {

  }
}

package flashcards.models.card;

import java.util.Map;
import java.util.stream.Stream;

public interface CardLibrary {
  Card create(String input, String output);

  Card update(Integer id, String input, String output);

  void delete(Integer id);

  Card get(Integer id);

  Card getRandom();

  Card get(String input);

  Map<Integer, Card> getActiveCards();

  Integer getBucket(Card card);

  Stream<Card> getCardsInBucket(Integer bucketNumber);

  int moveToNextBucket(Card card);

  int moveToPrevBucket(Card card);
}

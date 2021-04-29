package flashcards.models.card;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class InMemoryCardLibrary implements CardLibrary {

  static int counter = 0;

  Map<Integer, Card> cards = new HashMap<>();
  Map<Card, Integer> cardsBucket = new HashMap<>();

  @Override
  public Card create(String input, String output) {
    Card card = new Card(input, output, counter);
    cards.put(counter++, card);
    cardsBucket.put(card, 1);
    return card;
  }

  @Override
  public Card update(Integer id, String input, String output) {
    Card card = cards.get(id);
    if (card == null) {
      throw new IllegalArgumentException();
    }
    card.input = input;
    card.output = output;

    return card;
  }

  @Override
  public void delete(Integer id) {
    Card card = cards.remove(id);
    cardsBucket.remove(card);
  }

  @Override
  public Card get(Integer id) {
    return cards.get(id);
  }

  @Override
  public Card getRandom() {
    if (counter == 0) {
      return null;
    }
    int r = (int) (Math.random() * counter);
    return cards.get(r);
  }

  @Override
  public Card get(String input) {
    for (Card card : getActiveCards().values()) {
      if (card.input.equals(input)) {
        return card;
      }
    }
    return null;
  }

  @Override
  public Map<Integer, Card> getActiveCards() {
    return cards;
  }

  @Override
  public Integer getBucket(Card card) {
    return cardsBucket.get(card);
  }

  @Override
  public Stream<Card> getCardsInBucket(Integer bucketNumber) {
    return cardsBucket.entrySet().stream().filter(entry -> entry.getValue().equals(bucketNumber))
        .map(Entry::getKey);
  }

  @Override
  public int moveToNextBucket(Card card) {
    return cardsBucket.merge(card, 1, Integer::sum);
  }

  @Override
  public int moveToPrevBucket(Card card) {
    Integer bucket = cardsBucket.get(card);
    if (bucket == 1) {
      return 1;
    }
    return cardsBucket.put(card, bucket - 1);
  }

}

package flashcards.models.card;

import flashcards.models.card.Card;
import flashcards.models.card.CardLibrary;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCardLibrary implements CardLibrary {
  static int counter = 0;

  Map<Integer, Card> cards = new HashMap<>();

  @Override
  public Card create(String input, String output) {
    Card card = new Card(input, output);
    cards.put(counter++, card);
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
    cards.remove(id);
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
}

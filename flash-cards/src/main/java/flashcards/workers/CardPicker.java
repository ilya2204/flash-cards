package flashcards.workers;

import flashcards.models.card.Card;

public interface CardPicker {
  Card getCard();
  default void postAnalyzingCallback(Card card) {}
}

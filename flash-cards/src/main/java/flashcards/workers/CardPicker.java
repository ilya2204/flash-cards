package flashcards.workers;

import flashcards.models.card.Card;

public interface CardPicker {
  Card getCard();
  void postAnalyzingCallback(Card card);
}

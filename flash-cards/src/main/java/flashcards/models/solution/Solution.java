package flashcards.models.solution;

import flashcards.cli.ConsoleWorker;
import flashcards.models.card.Card;

import java.util.Date;

public class Solution {
  final public Date date;
  final public Card card;
  final public String result;
  final public Integer trainNumber;

  public Solution(Date date, Card card, String result) {
    this.date = date;
    this.card = card;
    this.result = result;
    this.trainNumber = ConsoleWorker.trainNumber;
  }


  public Solution(Card card, String result) {
    this.date = new Date(System.currentTimeMillis());
    this.card = card;
    this.result = result;
    this.trainNumber = ConsoleWorker.trainNumber;
  }
}

import java.util.Date;

public class Solution {
  final public Date date;
  final public Card card;
  final public String result;

  public Solution(Date date, Card card, String result) {
    this.date = date;
    this.card = card;
    this.result = result;
  }

  public Solution(Card card, String result) {
    this.date = new Date(System.currentTimeMillis());
    this.card = card;
    this.result = result;
  }
}

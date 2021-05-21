package flashcards.models.solution;

import static org.junit.jupiter.api.Assertions.*;

import flashcards.models.card.Card;
import flashcards.models.card.CardLibrary;
import flashcards.models.card.InMemoryCardLibrary;
import org.junit.jupiter.api.Test;

class InMemoryCardLibraryTest {

  @Test
  void testCreate() {
    CardLibrary cardLibrary = new InMemoryCardLibrary();
    String input, output;
    input = "input";
    output = "output";

    cardLibrary.create(input, output);
    assertEquals(cardLibrary.getActiveCards().size(), 1);

    input = "input with spaces";
    output = "output with spaces";
    cardLibrary.create(input, output);
    assertEquals(cardLibrary.getActiveCards().size(), 2);
  }

  @Test
  void testGet() {
    CardLibrary cardLibrary = new InMemoryCardLibrary();
    assertNull(cardLibrary.getRandom());

    String input, output;
    input = "input";
    output = "output";
    Card card = cardLibrary.create(input, output);

    assertEquals(card, cardLibrary.getRandom());
    assertEquals(card, cardLibrary.get(0));

  }

  @Test
  void testGetBucket() {
    CardLibrary cardLibrary = new InMemoryCardLibrary();
    String input = "input";
    String output = "output";

    Card card = cardLibrary.create(input, output);

    assertEquals(1, cardLibrary.getBucket(card));

    cardLibrary.moveToPrevBucket(card);
    cardLibrary.moveToPrevBucket(card);

    assertEquals(1, cardLibrary.getBucket(card));

    cardLibrary.moveToNextBucket(card);
    cardLibrary.moveToNextBucket(card);
    cardLibrary.moveToNextBucket(card);

    assertEquals(4, cardLibrary.getBucket(card));

    Card secondCard = cardLibrary.create(input, output);

    assertEquals(1, cardLibrary.getBucket(secondCard));
    cardLibrary.moveToNextBucket(secondCard);
    assertEquals(2, cardLibrary.getBucket(secondCard));

    cardLibrary.moveToPrevBucket(secondCard);
    cardLibrary.moveToPrevBucket(secondCard);

    assertEquals(1, cardLibrary.getBucket(secondCard));
  }

  @Test
  void testGetCardsInBucket() {
    CardLibrary cardLibrary = new InMemoryCardLibrary();
    Card card1 = cardLibrary.create("input", "output");
    Card card2 = cardLibrary.create("1", "2");
    Card card3 = cardLibrary.create("тест", "три");
    Card card4 = cardLibrary.create("ласт", "тест");

    assertEquals(4, cardLibrary.getCardsInBucket(1).count());

    cardLibrary.moveToNextBucket(card1);
    cardLibrary.moveToPrevBucket(card1);

    assertEquals(4, cardLibrary.getCardsInBucket(1).count());

    cardLibrary.moveToNextBucket(card1);
    assertEquals(3, cardLibrary.getCardsInBucket(1).count());

    cardLibrary.moveToNextBucket(card2);
    assertEquals(2, cardLibrary.getCardsInBucket(1).count());
    assertEquals(2, cardLibrary.getCardsInBucket(2).count());

    cardLibrary.moveToNextBucket(card1);
    cardLibrary.moveToNextBucket(card2);
    assertEquals(0, cardLibrary.getCardsInBucket(2).count());
    assertEquals(2, cardLibrary.getCardsInBucket(3).count());

    cardLibrary.moveToNextBucket(card3);
    cardLibrary.moveToNextBucket(card3);
    cardLibrary.moveToNextBucket(card4);
    cardLibrary.moveToNextBucket(card4);

    assertEquals(4, cardLibrary.getCardsInBucket(3).count());

  }
}

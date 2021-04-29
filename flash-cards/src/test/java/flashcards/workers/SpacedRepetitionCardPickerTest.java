package flashcards.workers;

import static org.junit.jupiter.api.Assertions.*;

import flashcards.cli.ConsoleWorker;
import flashcards.models.card.Card;
import flashcards.models.card.CardLibrary;
import flashcards.models.card.InMemoryCardLibrary;
import flashcards.models.solution.InMemorySolutionLibrary;
import flashcards.models.solution.SolutionLibrary;
import org.junit.jupiter.api.Test;

class SpacedRepetitionCardPickerTest {

  @Test
  void testGetCardsForCurrentTraining() {
    ConsoleWorker.trainNumber = 1;
    CardLibrary cardLibrary = new InMemoryCardLibrary();
    SolutionLibrary solutionLibrary = new InMemorySolutionLibrary();
    SpacedRepetitionCardPicker picker = new SpacedRepetitionCardPicker(
        cardLibrary, solutionLibrary);

    assertEquals(0, picker.cardsForCurrentTraining().count());

    Card card1 = cardLibrary.create("input", "output");
    Card card2 = cardLibrary.create("1", "2");
    Card card3 = cardLibrary.create("тест", "три");
    Card card4 = cardLibrary.create("ласт", "тест");

    assertEquals(4, picker.cardsForCurrentTraining().count());

    cardLibrary.moveToNextBucket(card1); // -> 2
    assertEquals(3, picker.cardsForCurrentTraining().count());

    cardLibrary.moveToNextBucket(card1); // -> 3
    cardLibrary.moveToNextBucket(card2); // -> 2
    cardLibrary.moveToNextBucket(card2); // -> 3
    assertEquals(2, picker.cardsForCurrentTraining().count());

    ConsoleWorker.trainNumber = 4;
    assertEquals(2, picker.cardsForCurrentTraining().count());

    cardLibrary.moveToPrevBucket(card1); // -> 2
    assertEquals(3, picker.cardsForCurrentTraining().count());

    ConsoleWorker.trainNumber = 5;
    cardLibrary.moveToNextBucket(card3); // -> 2
    cardLibrary.moveToNextBucket(card4); // -> 2
    assertEquals(0, picker.cardsForCurrentTraining().count());

    ConsoleWorker.trainNumber = 6;
    cardLibrary.moveToNextBucket(card3); // -> 3
    assertEquals(4, picker.cardsForCurrentTraining().count());

    ConsoleWorker.trainNumber = 7;
    cardLibrary.moveToPrevBucket(card4); // -> 1
    Card card5 = cardLibrary.create("five", "пять");
    assertEquals(2, picker.cardsForCurrentTraining().count());

  }

  @Test
  void testpostAnalyzingCallback() {
    ConsoleWorker.trainNumber = 1;
    CardLibrary cardLibrary = new InMemoryCardLibrary();
    SolutionLibrary solutionLibrary = new InMemorySolutionLibrary();
    SpacedRepetitionCardPicker picker = new SpacedRepetitionCardPicker(
        cardLibrary, solutionLibrary);

    Card card1 = cardLibrary.create("input", "output");
    Card card2 = cardLibrary.create("1", "2");
    Card card3 = cardLibrary.create("тест", "три");
    Card card4 = cardLibrary.create("ласт", "тест");
    Card card5 = cardLibrary.create("ez", "win");
    Card card6 = cardLibrary.create("python", "thebest");
    Card card7 = cardLibrary.create("зачем вообще", "писать тесты");
    Card card8 = cardLibrary.create("я не", "понимаю");

    assertEquals(8, picker.cardsForCurrentTraining().count());

    solutionLibrary.create(card1.output, card1); // -> 2
    solutionLibrary.create(card2.output, card2); // -> 2

    picker.postAnalyzingCallback(card1);
    picker.postAnalyzingCallback(card2);

    assertEquals(2, cardLibrary.getBucket(card1));
    assertEquals(2, cardLibrary.getBucket(card2));

    assertEquals(6, picker.cardsForCurrentTraining().count());

    ConsoleWorker.trainNumber = 2;
    assertEquals(8, picker.cardsForCurrentTraining().count());

    solutionLibrary.create(card1.output, card1); // -> 2
    solutionLibrary.create(card2.output, card2); // -> 2

    picker.postAnalyzingCallback(card1);
    picker.postAnalyzingCallback(card2);

    assertEquals(2, cardLibrary.getBucket(card1));
    assertEquals(2, cardLibrary.getBucket(card2));

    ConsoleWorker.trainNumber = 3;
    assertEquals(6, picker.cardsForCurrentTraining().count());

    assertEquals(6, picker.cardsForCurrentTraining().count());

    solutionLibrary.create(card3.output, card3); // -> 2
    solutionLibrary.create(card4.output, card4); // -> 2
    solutionLibrary.create(card5.output, card5); // -> 2
    solutionLibrary.create(card6.output, card6); // -> 2
    solutionLibrary.create(card7.output, card7); // -> 2
    solutionLibrary.create(card8.output, card8); // -> 2

    picker.postAnalyzingCallback(card3);
    picker.postAnalyzingCallback(card4);
    picker.postAnalyzingCallback(card5);
    picker.postAnalyzingCallback(card6);
    picker.postAnalyzingCallback(card7);
    picker.postAnalyzingCallback(card8);

    assertEquals(2, cardLibrary.getBucket(card3));
    assertEquals(2, cardLibrary.getBucket(card5));
    assertEquals(2, cardLibrary.getBucket(card8));

    assertEquals(0, picker.cardsForCurrentTraining().count());

    ConsoleWorker.trainNumber = 4;
    assertEquals(8, picker.cardsForCurrentTraining().count());

    ConsoleWorker.trainNumber = 5;
    assertEquals(0, picker.cardsForCurrentTraining().count());

    ConsoleWorker.trainNumber = 6;
    assertEquals(2, cardLibrary.getBucket(card5));
    assertEquals(2, cardLibrary.getBucket(card7));
    assertEquals(8, picker.cardsForCurrentTraining().count());

    solutionLibrary.create(card3.output, card3); // -> 2

    assertEquals(7, picker.cardsForCurrentTraining().count());

    solutionLibrary.create(card4.output, card4); // -> 2
    solutionLibrary.create(card5.output, card5); // -> 2

    assertEquals(5, picker.cardsForCurrentTraining().count());

    solutionLibrary.create(card6.output, card6); // -> 2
    solutionLibrary.create(card7.output, card7); // -> 2
    solutionLibrary.create(card8.output, card8); // -> 2

    picker.postAnalyzingCallback(card3);
    picker.postAnalyzingCallback(card4);
    picker.postAnalyzingCallback(card5);
    picker.postAnalyzingCallback(card6);
    picker.postAnalyzingCallback(card7);
    picker.postAnalyzingCallback(card8);

    assertEquals(2, cardLibrary.getBucket(card5));
    assertEquals(2, cardLibrary.getBucket(card7));

    assertEquals(2, picker.cardsForCurrentTraining().count());



    ConsoleWorker.trainNumber = 7;

    solutionLibrary.create(card3.output, card3); // -> 3
    solutionLibrary.create(card4.output, card4); // -> 3
    solutionLibrary.create(card5.output, card5); // -> 3
    solutionLibrary.create(card6.output, card6); // -> 3
    solutionLibrary.create(card7.output, card7); // -> 3
    solutionLibrary.create(card8.output, card8); // -> 3

    picker.postAnalyzingCallback(card3);
    picker.postAnalyzingCallback(card4);
    picker.postAnalyzingCallback(card5);
    picker.postAnalyzingCallback(card6);
    picker.postAnalyzingCallback(card7);
    picker.postAnalyzingCallback(card8);

    assertEquals(3, cardLibrary.getBucket(card5));
    assertEquals(3, cardLibrary.getBucket(card7));


    ConsoleWorker.trainNumber = 8;

    solutionLibrary.create("ez", card3); // -> 2
    solutionLibrary.create("ez", card4); // -> 2
    solutionLibrary.create("ez", card5); // -> 2
    solutionLibrary.create("ez", card6); // -> 2
    solutionLibrary.create("ez", card7); // -> 2
    solutionLibrary.create("ez", card8); // -> 2

    picker.postAnalyzingCallback(card3);
    picker.postAnalyzingCallback(card4);
    picker.postAnalyzingCallback(card5);
    picker.postAnalyzingCallback(card6);
    picker.postAnalyzingCallback(card7);
    picker.postAnalyzingCallback(card8);

    assertEquals(2, cardLibrary.getBucket(card5));
    assertEquals(2, cardLibrary.getBucket(card7));


  }

}
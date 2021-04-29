package flashcards.workers;

import static org.junit.jupiter.api.Assertions.*;

import flashcards.cli.ConsoleWorker;
import flashcards.models.card.Card;
import flashcards.models.card.CardLibrary;
import flashcards.models.card.InMemoryCardLibrary;
import flashcards.models.solution.InMemorySolutionLibrary;
import flashcards.models.solution.SolutionLibrary;
import org.junit.jupiter.api.Test;

class MostMistakeCardPickerTest {

  @Test
  void testGetCard() {
    CardLibrary cardLibrary = new InMemoryCardLibrary();
    SolutionLibrary solutionLibrary = new InMemorySolutionLibrary();
    MostMistakeCardPicker picker = new MostMistakeCardPicker(
        cardLibrary, solutionLibrary);

    Card card1 = cardLibrary.create("input", "output");

    assertEquals(card1, picker.getCard());

    Card card2 = cardLibrary.create("1", "2");

    Card card3 = cardLibrary.create("тест", "три");
    Card card4 = cardLibrary.create("ласт", "тест");
    Card card5 = cardLibrary.create("ez", "win");
    Card card6 = cardLibrary.create("python", "thebest");
    Card card7 = cardLibrary.create("зачем вообще", "писать тесты");
    Card card8 = cardLibrary.create("я не", "понимаю");

  }

}
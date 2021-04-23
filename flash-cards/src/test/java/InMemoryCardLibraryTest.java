import static org.junit.jupiter.api.Assertions.*;

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
    assertEquals(cardLibrary.getRandom(), null);

    String input, output;
    input = "input";
    output = "output";
    Card card = cardLibrary.create(input, output);

    assertEquals(cardLibrary.getRandom(), card);
    assertEquals(cardLibrary.get(0), card);

  }



}
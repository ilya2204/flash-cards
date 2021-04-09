import java.util.Map;

public interface CardLibrary {
  public Card create(String input, String output);

  public Card update(Integer id, String input, String output);

  public void delete(Integer id);

  public Card get(Integer id);


  public Map<Integer, Card> getActiveCards();
}

package flashcards.workers;

import flashcards.models.card.Card;

interface SolutionReportGenerator {
  String generateReport(Card card, String input);
}

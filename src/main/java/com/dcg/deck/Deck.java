package com.dcg.deck;

import java.util.ArrayDeque;
import java.util.Deque;

public class Deck {

  private final Deque<Card> all = new ArrayDeque<>();
  private final Deque<Card> drawPile = new ArrayDeque<>();
  private final Deque<Card> discardPile = new ArrayDeque<>();

  public void add(Card card) {
    all.push(card);
    drawPile.push(card);
  }

  public Card draw() {
    if (drawPile.isEmpty()) {
      // TODO: support shuffle
      drawPile.addAll(discardPile);
      discardPile.clear();
    }
    return drawPile.pop();
  }

  public void discard(Card card) {
    discardPile.push(card);
  }
}

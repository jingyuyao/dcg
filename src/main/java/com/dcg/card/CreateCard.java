package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.game.CreateEntity;
import com.dcg.location.Deck;

public class CreateCard extends CreateEntity {
  private final String name;
  private final int cost;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Deck> mDeck;

  public CreateCard(String name, int cost) {
    this.name = name;
    this.cost = cost;
  }

  @Override
  protected void run() {
    int cardEntity = createEntity();
    Card card = mCard.create(cardEntity);
    card.name = name;
    card.cost = cost;
    mDeck.create(cardEntity);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), name);
  }
}

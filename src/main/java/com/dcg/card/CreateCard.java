package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.game.CreateEntity;
import com.dcg.location.Deck;
import com.dcg.target.Target;

public class CreateCard extends CreateEntity {
  private final int cost;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Deck> mDeck;

  public CreateCard(String name, int cost) {
    super(name);
    this.cost = cost;
  }

  @Override
  protected void run(Target target) {
    int cardEntity = createEntity(target);
    Card card = mCard.create(cardEntity);
    card.cost = cost;
    mDeck.create(cardEntity);
  }
}

package com.dcg.card;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.effect.Effect;
import com.dcg.location.Deck;
import com.dcg.ownership.Own;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateCard extends Command {
  private final String name;
  private final int cost;
  private final List<Command> onCreateEffects = new ArrayList<>();
  @Wire CommandChain commandChain;
  protected World world;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Effect> mEffect;
  protected ComponentMapper<Deck> mDeck;

  public CreateCard(String name, int cost) {
    this.name = name;
    this.cost = cost;
  }

  public CreateCard addOnCreateEffects(Command... effects) {
    this.onCreateEffects.addAll(Arrays.asList(effects));
    return this;
  }

  @Override
  protected void run() {
    int cardEntity = world.create();
    Card card = mCard.create(cardEntity);
    card.name = name;
    card.cost = cost;
    Effect effect = mEffect.create(cardEntity);
    effect.onCreate = onCreateEffects;
    mDeck.create(cardEntity);
    if (owner != -1) {
      commandChain.addStart(new Own(owner, cardEntity));
    }
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), name);
  }
}

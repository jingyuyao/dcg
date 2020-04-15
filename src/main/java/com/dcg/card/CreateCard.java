package com.dcg.card;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.Deck;
import com.dcg.ownership.Own;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateCard extends Command {
  private final String name;
  private final int cost;
  private final List<Command> effects = new ArrayList<>();
  private int owner = -1;
  @Wire CommandChain commandChain;
  protected World world;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Deck> mDeck;

  public CreateCard(String name, int cost) {
    this.name = name;
    this.cost = cost;
  }

  public CreateCard setOwner(int owner) {
    this.owner = owner;
    return this;
  }

  public CreateCard addEffects(Command... effects) {
    this.effects.addAll(Arrays.asList(effects));
    return this;
  }

  @Override
  public void run() {
    int cardEntity = world.create();
    Card card = mCard.create(cardEntity);
    card.name = name;
    card.cost = cost;
    card.effects = effects;
    mDeck.create(cardEntity);
    if (owner != -1) {
      commandChain.addStart(new Own(owner, cardEntity));
    }
  }
}

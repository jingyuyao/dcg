package com.dcg.card;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.ownership.Own;

public class CreateCard extends Command {
  private final String name;
  private final Class<? extends Location> location;
  private final int owner;
  @Wire CommandChain commandChain;
  World world;
  ComponentMapper<Card> mCard;

  public CreateCard(String name, Class<? extends Location> location) {
    this.name = name;
    this.location = location;
    this.owner = -1;
  }

  public CreateCard(String name, Class<? extends Location> location, int owner) {
    this.name = name;
    this.location = location;
    this.owner = owner;
  }

  @Override
  public void run() {
    int cardEntity = world.create();
    Card card = mCard.create(cardEntity);
    card.name = name;
    if (owner == -1) {
      commandChain.addStart(new MoveLocation(cardEntity, location));
    } else {
      commandChain.addStart(new MoveLocation(cardEntity, location), new Own(owner, cardEntity));
    }
  }

  @Override
  public String toString() {
    return super.toString() + name;
  }
}

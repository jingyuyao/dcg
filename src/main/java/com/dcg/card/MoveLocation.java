package com.dcg.card;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;

public class MoveLocation implements Command {

  private final int cardEntity;
  private final Class<? extends Location> location;
  World world;
  ComponentMapper<Card> mCard;

  public MoveLocation(int cardEntity, Class<? extends Location> location) {
    this.cardEntity = cardEntity;
    this.location = location;
  }

  @Override
  public void run() {
    for (Class<? extends Location> clazz : Card.LOCATIONS) {
      ComponentMapper<? extends Location> mapper = world.getMapper(clazz);
      mapper.set(cardEntity, location.equals(clazz));
    }
  }

  @Override
  public String toString() {
    return "MoveLocation " + mCard.get(cardEntity).name + " to " + location.getSimpleName();
  }
}

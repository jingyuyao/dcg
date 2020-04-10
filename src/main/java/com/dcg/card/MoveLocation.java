package com.dcg.card;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;

public class MoveLocation extends Command {
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
    for (Class<? extends Location> clazz : Location.ALL) {
      ComponentMapper<? extends Location> mapper = world.getMapper(clazz);
      mapper.set(cardEntity, location.equals(clazz));
    }
  }

  @Override
  public String toString() {
    return super.toString() + mCard.get(cardEntity) + " to " + location.getSimpleName();
  }
}

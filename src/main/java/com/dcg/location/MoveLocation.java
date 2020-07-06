package com.dcg.location;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import java.util.Arrays;
import java.util.List;

public class MoveLocation extends AbstractCommandBuilder {
  private static final List<Class<? extends Location>> ALL =
      Arrays.asList(
          ForgeDeck.class,
          ForgeRow.class,
          ThroneDeck.class,
          MercenaryDeck.class,
          PlayerDeck.class,
          DiscardPile.class,
          Hand.class,
          PlayArea.class);
  private final Class<? extends Location> location;

  public MoveLocation(Class<? extends Location> location) {
    this.location = location;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandData args) {
    for (Class<? extends Location> clazz : ALL) {
      ComponentMapper<? extends Location> mapper = world.getMapper(clazz);
      mapper.set(originEntity, location.equals(clazz));
    }
  }

  @Override
  public String toString() {
    return String.format("%s to %s", super.toString(), location.getSimpleName());
  }
}

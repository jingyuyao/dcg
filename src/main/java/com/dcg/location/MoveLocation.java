package com.dcg.location;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBase;
import java.util.Arrays;
import java.util.List;

public class MoveLocation extends CommandBase {
  private static final List<Class<? extends Location>> ALL =
      Arrays.asList(Deck.class, ForgeRow.class, DiscardPile.class, Hand.class, PlayArea.class);
  private final Class<? extends Location> location;

  public MoveLocation(Class<? extends Location> location) {
    this.location = location;
  }

  @Override
  protected void run() {
    for (Class<? extends Location> clazz : ALL) {
      ComponentMapper<? extends Location> mapper = world.getMapper(clazz);
      mapper.set(sourceEntity, location.equals(clazz));
    }
  }

  @Override
  public String toString() {
    return String.format("%s to %s", super.toString(), location.getSimpleName());
  }
}

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
          PlayArea.class,
          BanishedPile.class);
  private final Class<? extends Location> location;

  public MoveLocation(Class<? extends Location> location) {
    this.location = location;
  }

  @Override
  protected void run(CommandData data) {
    for (Class<? extends Location> clazz : ALL) {
      ComponentMapper<? extends Location> mapper = world.getMapper(clazz);
      mapper.set(data.getOriginEntity(), location.equals(clazz));
    }
  }

  @Override
  protected String getDescription(CommandData data) {
    return String.format(
        "%s to %s", coreSystem.toName(data.getOriginEntity()), location.getSimpleName());
  }
}

package com.dcg.location;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.card.Card;
import com.dcg.command.Command;
import java.util.Arrays;
import java.util.List;

public class MoveLocation extends Command {
  private static final List<Class<? extends Location>> ALL =
      Arrays.asList(
          Deck.class,
          ForgeRow.class,
          DiscardPile.class,
          Hand.class,
          PlayArea.class,
          BattleArea.class);
  private final int cardEntity;
  private final Class<? extends Location> location;
  protected World world;
  protected ComponentMapper<Card> mCard;

  public MoveLocation(int cardEntity, Class<? extends Location> location) {
    this.cardEntity = cardEntity;
    this.location = location;
  }

  @Override
  public void run() {
    for (Class<? extends Location> clazz : ALL) {
      ComponentMapper<? extends Location> mapper = world.getMapper(clazz);
      mapper.set(cardEntity, location.equals(clazz));
    }
  }

  @Override
  public String toString() {
    return super.toString() + mCard.get(cardEntity) + " to " + location.getSimpleName();
  }
}

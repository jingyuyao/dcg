package com.dcg.location;

import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;
import java.util.Arrays;
import java.util.List;

public class MoveLocation extends AbstractCommandBuilder {
  private static final List<Class<? extends Location>> ALL =
      Arrays.asList(Deck.class, ForgeRow.class, DiscardPile.class, Hand.class, PlayArea.class);
  private final Class<? extends Location> location;
  protected ComponentMapper<Card> mCard;

  public MoveLocation(Class<? extends Location> location) {
    this.location = location;
    addTargetConditions(target -> target.get().stream().allMatch(mCard::has));
  }

  @Override
  protected void run(Target target) {
    target
        .get()
        .forEach(
            cardEntity -> {
              for (Class<? extends Location> clazz : ALL) {
                ComponentMapper<? extends Location> mapper = world.getMapper(clazz);
                mapper.set(cardEntity, location.equals(clazz));
              }
            });
  }

  @Override
  public String toString() {
    return String.format("%s to %s ", super.toString(), location.getSimpleName());
  }
}

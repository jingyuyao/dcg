package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.location.Hand;
import com.dcg.ownership.Owned;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.AdvanceTurn;
import com.dcg.turn.Turn;
import java.util.ArrayList;
import java.util.List;

@All({Player.class, Turn.class})
public class PlayerActionSystem extends IteratingSystem {

  private final List<Command> actions = new ArrayList<>();
  OwnershipSystem ownershipSystem;

  @Override
  protected void process(int entityId) {
    actions.clear();
    Aspect.Builder hand = Aspect.all(Card.class, Owned.class, Hand.class);
    for (int cardEntity : ownershipSystem.filter(hand, entityId)) {
      actions.add(new PlayCard(cardEntity));
    }
    if (actions.isEmpty()) {
      actions.add(new AdvanceTurn());
    }
    actions.add(new BuyCard(entityId));
  }

  /** Snapshot the current actions so executing the results doesn't change the content. */
  public List<Command> getActions() {
    return new ArrayList<>(actions);
  }
}

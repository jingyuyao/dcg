package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.dcg.battle.Block;
import com.dcg.battle.Blocking;
import com.dcg.card.Card;
import com.dcg.card.Strength;
import com.dcg.command.Command;
import com.dcg.location.BattleArea;
import com.dcg.location.Hand;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.AdvanceTurn;
import com.dcg.turn.Turn;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@All({Player.class, Turn.class})
public class PlayerActionSystem extends IteratingSystem {

  private List<Command> actions = Collections.emptyList();
  OwnershipSystem ownershipSystem;

  @Override
  protected void process(int entityId) {
    actions = new ArrayList<>();
    List<Command> playCards =
        ownershipSystem.getOwnedBy(entityId, Aspect.all(Card.class, Hand.class)).stream()
            .map(PlayCard::new)
            .collect(Collectors.toList());
    actions.addAll(playCards);
    if (playCards.isEmpty()) {
      actions.add(new AdvanceTurn());
    }
    actions.add(new BuyCard(entityId));
    boolean canBlock =
        ownershipSystem
                .getOwnedBy(
                    entityId, Aspect.all(BattleArea.class, Strength.class).exclude(Blocking.class))
                .size()
            > 0;
    if (canBlock) {
      actions.add(new Block());
    }
  }

  public List<Command> getActions() {
    return actions;
  }
}

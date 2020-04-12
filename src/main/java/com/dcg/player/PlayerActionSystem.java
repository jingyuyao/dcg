package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.systems.IteratingSystem;
import com.dcg.battle.Block;
import com.dcg.card.Card;
import com.dcg.card.Strength;
import com.dcg.command.Command;
import com.dcg.location.BattleArea;
import com.dcg.location.Hand;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.AdvanceTurn;
import com.dcg.turn.Turn;
import com.dcg.util.AspectSystem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@All({Player.class, Turn.class})
public class PlayerActionSystem extends IteratingSystem {
  private List<Command> actions = Collections.emptyList();
  protected AspectSystem aspectSystem;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Player> mPlayer;

  @Override
  protected void process(int entityId) {
    actions = new ArrayList<>();
    List<Command> playCards = getPlayCards(entityId);
    if (playCards.isEmpty()) {
      actions.add(new AdvanceTurn());
    } else {
      actions.addAll(playCards);
    }
    getBuyCard(entityId).ifPresent(actions::add);
    getBlock(entityId).ifPresent(actions::add);
  }

  public List<Command> getActions() {
    return actions;
  }

  private List<Command> getPlayCards(int playerEntity) {
    return ownershipSystem.getOwnedBy(playerEntity, Aspect.all(Card.class, Hand.class)).stream()
        .map(PlayCard::new)
        .collect(Collectors.toList());
  }

  private Optional<Command> getBuyCard(int playerEntity) {
    int availablePower = mPlayer.get(playerEntity).power;
    return availablePower > 0
        ? Optional.of(new BuyCard(playerEntity, availablePower))
        : Optional.empty();
  }

  private Optional<Command> getBlock(int playerEntity) {
    List<Integer> battleArea = aspectSystem.get(Aspect.all(BattleArea.class, Strength.class));
    boolean hasAttacker = false;
    boolean hasDefender = false;
    for (int entity : battleArea) {
      boolean isOwnedByPlayer = ownershipSystem.isOwnedBy(playerEntity, entity);
      hasAttacker |= !isOwnedByPlayer;
      hasDefender |= isOwnedByPlayer;
    }
    return hasAttacker && hasDefender ? Optional.of(new Block()) : Optional.empty();
  }
}

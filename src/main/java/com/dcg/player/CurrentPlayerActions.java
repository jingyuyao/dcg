package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.World;
import com.dcg.card.Card;
import com.dcg.card.Hand;
import com.dcg.command.Command;
import com.dcg.ownership.Owned;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.turn.AdvanceTurn;
import com.dcg.turn.TurnSystem;
import java.util.ArrayList;
import java.util.List;

public class CurrentPlayerActions extends Command {

  private final List<Command> actions = new ArrayList<>();
  World world;
  TurnSystem turnSystem;
  OwnershipSystem ownershipSystem;

  @Override
  public void run() {
    int playerEntity = turnSystem.getCurrentPlayerEntity();
    Aspect.Builder hand = Aspect.all(Card.class, Owned.class, Hand.class);
    for (int cardEntity : ownershipSystem.filter(hand, playerEntity)) {
      actions.add(new PlayCard(cardEntity));
    }
    if (actions.isEmpty()) {
      actions.add(new AdvanceTurn());
    }
    actions.add(new BuyCard(playerEntity));
    // TODO: get rid of this
    for (Command command : actions) {
      world.inject(command);
    }
  }

  public List<Command> getCommands() {
    return actions;
  }
}

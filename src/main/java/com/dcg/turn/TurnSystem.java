package com.dcg.turn;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.battle.Battle;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.player.DiscardPlayArea;
import com.dcg.player.DrawCard;
import com.dcg.player.Player;
import java.util.ArrayList;
import java.util.List;

@All({Player.class, Turn.class})
public class TurnSystem extends BaseEntitySystem {
  @Wire protected CommandChain commandChain;
  protected ComponentMapper<Turn> mTurn;

  public int getCurrentPlayerEntity() {
    IntBag entities = getEntityIds();
    assert entities.size() < 2;
    return entities.size() == 1 ? getEntityIds().get(0) : -1;
  }

  public Turn getCurrentTurn() {
    return mTurn.get(getEntityIds().get(0));
  }

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    List<Command> commands = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      commands.add(new DrawCard(entityId));
    }
    commandChain.addEnd(commands);
  }

  @Override
  protected void removed(int entityId) {
    super.removed(entityId);
    commandChain.addEnd(new DiscardPlayArea(entityId));
    Turn turn = mTurn.get(entityId);
    if (turn.previousPlayerEntity != -1) {
      commandChain.addEnd(new Battle(turn.previousPlayerEntity, entityId));
    }
  }

  @Override
  protected void processSystem() {
    // No-op.
  }
}

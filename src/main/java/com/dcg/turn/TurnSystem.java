package com.dcg.turn;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.battle.AttackPlayer;
import com.dcg.command.CommandChain;
import com.dcg.player.DiscardPlayArea;
import com.dcg.player.DrawCards;
import com.dcg.player.PlayHand;
import com.dcg.player.Player;

@All({Player.class, Turn.class})
public class TurnSystem extends BaseEntitySystem {
  @Wire protected CommandChain commandChain;
  protected ComponentMapper<Turn> mTurn;
  private int currentPlayerEntity = -1;

  public int getCurrentPlayerEntity() {
    return currentPlayerEntity;
  }

  public Turn getCurrentTurn() {
    return mTurn.get(currentPlayerEntity);
  }

  @Override
  protected void inserted(int playerEntity) {
    super.inserted(playerEntity);
    currentPlayerEntity = playerEntity;
    commandChain.addEnd(new PlayHand(playerEntity));
  }

  @Override
  protected void removed(int playerEntity) {
    super.removed(playerEntity);
    commandChain.addEnd(new DiscardPlayArea(playerEntity));
    Turn turn = mTurn.get(playerEntity);
    if (turn.previousPlayerEntity != -1) {
      commandChain.addEnd(new AttackPlayer(turn.previousPlayerEntity, playerEntity));
    }
    commandChain.addEnd(new DrawCards(playerEntity, 5));
  }

  @Override
  protected void processSystem() {
    // TODO: loop through turn.commands and execute if preconditions are met
  }
}

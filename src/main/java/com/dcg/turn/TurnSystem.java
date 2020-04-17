package com.dcg.turn;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.dcg.player.Player;

@All({Player.class, Turn.class})
public class TurnSystem extends BaseEntitySystem {
  protected ComponentMapper<Turn> mTurn;
  private int currentPlayerEntity = -1;

  // TODO: i don't like this
  public int getPlayerEntity() {
    return currentPlayerEntity;
  }

  public Turn getTurn() {
    return mTurn.getSafe(currentPlayerEntity, new Turn());
  }

  @Override
  protected void inserted(int playerEntity) {
    currentPlayerEntity = playerEntity;
  }

  @Override
  protected void processSystem() {}
}

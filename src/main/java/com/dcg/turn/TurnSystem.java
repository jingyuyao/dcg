package com.dcg.turn;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.artemis.utils.IntBag;
import com.dcg.command.Command;
import com.dcg.command.Commands;
import com.dcg.player.Player;

@All(Player.class)
public class TurnSystem extends BaseEntitySystem {
  @Wire Commands commands;
  ComponentMapper<Player> mPlayer;
  ComponentMapper<Turn> mTurn;

  @Override
  protected void processSystem() {
    for (Command command : commands.getCurrent()) {
      if (command instanceof AdvanceTurn) {
        advanceTurn();
      }
    }
    System.out.println("current player: " + mPlayer.get(getCurrentPlayer()).name);
  }

  private void advanceTurn() {
    int currentPlayer = getCurrentPlayer();
    IntBag players = getEntityIds();
    int currentPlayerIndex = players.indexOf(currentPlayer);
    int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
    int nextPlayer = players.get(nextPlayerIndex);
    mTurn.remove(currentPlayer);
    mTurn.create(nextPlayer);
  }

  private int getCurrentPlayer() {
    for (int player : getEntityIds().getData()) {
      if (mTurn.has(player)) {
        return player;
      }
    }
    throw new RuntimeException("No current player?!");
  }
}

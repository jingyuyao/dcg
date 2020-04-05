package com.dcg.turn;

import com.artemis.Aspect;
import com.artemis.AspectSubscriptionManager;
import com.artemis.ComponentMapper;
import com.artemis.utils.IntBag;
import com.dcg.command.Command;
import com.dcg.player.Player;

public class AdvanceTurn implements Command {
  AspectSubscriptionManager manager;
  ComponentMapper<Turn> mTurn;

  @Override
  public void run() {
    int currentPlayer = manager.get(Aspect.all(Player.class, Turn.class)).getEntities().get(0);
    IntBag players = manager.get(Aspect.all(Player.class)).getEntities();
    int currentPlayerIndex = players.indexOf(currentPlayer);
    int nextPlayerIndex = (currentPlayerIndex + 1) % players.size();
    int nextPlayer = players.get(nextPlayerIndex);
    mTurn.remove(currentPlayer);
    mTurn.create(nextPlayer);
  }
}

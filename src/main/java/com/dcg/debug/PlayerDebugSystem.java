package com.dcg.debug;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.utils.IntBag;
import com.dcg.player.Player;
import com.dcg.turn.Turn;

@All(Player.class)
public class PlayerDebugSystem extends BaseEntitySystem {

  ComponentMapper<Player> mPlayer;
  ComponentMapper<Turn> mTurn;

  @Override
  protected void processSystem() {
    // No-op
  }

  public void printDebug() {
    System.out.println("players");
    IntBag bag = getEntityIds();
    int[] entities = bag.getData();
    for (int i = 0, s = bag.size(); i < s; i++) {
      int entity = entities[i];
      System.out.print(mTurn.has(entity) ? " *" : "  ");
      System.out.print(mPlayer.get(entity));
      System.out.println();
    }
    System.out.println();
  }
}

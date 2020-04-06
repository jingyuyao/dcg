package com.dcg.player;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.dcg.command.CommandSystem;
import com.dcg.turn.Turn;

@All({Player.class, Turn.class})
public class PlayerTurnSystem extends BaseEntitySystem {

  CommandSystem commandSystem;
  ComponentMapper<Player> mPlayer;

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    Player player = mPlayer.get(entityId);
    commandSystem.run(
        new DrawCard(player),
        new DrawCard(player),
        new DrawCard(player),
        new DrawCard(player),
        new DrawCard(player));
  }

  @Override
  protected void removed(int entityId) {
    super.removed(entityId);
    Player player = mPlayer.get(entityId);
    DiscardCard[] discardCards = new DiscardCard[player.hand.size()];
    for (int i = 0; i < player.hand.size(); i++) {
      discardCards[i] = new DiscardCard(player, player.hand.get(i));
    }
    player.hand.clear();
    commandSystem.run(discardCards);
  }

  @Override
  protected void processSystem() {
    // No-op.
  }
}

package com.dcg.turn;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.player.Player;
import java.util.List;
import java.util.stream.Collectors;

public class AdvancePlayerStep extends AbstractCommandBuilder {
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Turn> mTurn;

  @Override
  protected void run(CommandData data) {
    int originEntity = data.getOriginEntity();
    List<Integer> allPlayerEntities =
        coreSystem.getStream(Aspect.all(Player.class)).collect(Collectors.toList());
    int currentPlayerIndex = allPlayerEntities.indexOf(originEntity);
    int nextPlayerIndex = (currentPlayerIndex + 1) % allPlayerEntities.size();
    // Ignore players that are already dead.
    // TODO: tag dead players
    while (mPlayer.get(nextPlayerIndex).hp <= 0) {
      nextPlayerIndex = (nextPlayerIndex + 1) % allPlayerEntities.size();
    }
    int nextPlayer = allPlayerEntities.get(nextPlayerIndex);
    mTurn.remove(originEntity);
    Turn turn = mTurn.create(nextPlayer);
    turn.previousPlayerEntity = originEntity;
  }
}

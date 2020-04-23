package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import java.util.List;
import java.util.stream.Collectors;

public class AdvancePlayerStep extends AbstractCommandBuilder {
  protected ComponentMapper<Turn> mTurn;

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    List<Integer> allPlayerEntities =
        coreSystem.getStream(Aspect.all(Player.class)).collect(Collectors.toList());
    int currentPlayerIndex = allPlayerEntities.indexOf(originEntity);
    int nextPlayerIndex = (currentPlayerIndex + 1) % allPlayerEntities.size();
    int nextPlayer = allPlayerEntities.get(nextPlayerIndex);
    mTurn.remove(originEntity);
    mTurn.create(nextPlayer);
  }
}

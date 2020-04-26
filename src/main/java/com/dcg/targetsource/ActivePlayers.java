package com.dcg.targetsource;

import com.dcg.game.CoreSystem;
import java.util.stream.Stream;

public class ActivePlayers extends TargetSource {
  protected CoreSystem coreSystem;

  @Override
  protected Stream<Integer> getSource(int originEntity) {
    return coreSystem.getActivePlayerEntities();
  }
}

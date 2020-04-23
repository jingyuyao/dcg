package com.dcg.target;

import com.artemis.Aspect;
import com.dcg.game.CoreSystem;
import com.dcg.player.Player;
import java.util.stream.Stream;

public class AllPlayers extends TargetSource {
  protected CoreSystem coreSystem;

  @Override
  protected Stream<Integer> getSource(int originEntity) {
    return coreSystem.getStream(Aspect.all(Player.class));
  }
}

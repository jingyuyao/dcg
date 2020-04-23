package com.dcg.target;

import com.dcg.game.CoreSystem;
import java.util.stream.Stream;

public class AttackingUnits extends TargetSource {
  protected CoreSystem coreSystem;

  @Override
  protected Stream<Integer> getSource(int originEntity) {
    return coreSystem.getAttackingEntities();
  }
}

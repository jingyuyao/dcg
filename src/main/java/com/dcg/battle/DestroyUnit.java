package com.dcg.battle;

import com.dcg.command.CommandData;
import java.util.List;

public class DestroyUnit extends UnitEffectBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandData args) {
    targets.forEach(world::delete);
  }
}

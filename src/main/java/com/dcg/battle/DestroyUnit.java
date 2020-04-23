package com.dcg.battle;

import com.dcg.command.CommandArgs;
import java.util.List;

public class DestroyUnit extends UnitEffectBuilder {
  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    targets.forEach(world::delete);
  }
}

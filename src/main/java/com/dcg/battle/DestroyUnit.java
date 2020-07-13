package com.dcg.battle;

import com.dcg.command.CommandData;

public class DestroyUnit extends UnitEffectBuilder {
  @Override
  protected void run(CommandData data) {
    data.getTargets().forEach(coreSystem::remove);
  }
}

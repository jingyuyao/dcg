package com.dcg.battle;

import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;

public class DestroyUnit extends AbstractCommandBuilder {
  @Override
  protected void run(CommandData data) {
    data.getTargets().forEach(coreSystem::remove);
  }

  @Override
  protected String getDescription(CommandData data) {
    // TODO: don't get destroy from block
    return String.format("destroys %s", coreSystem.toNames(data.getTargets()));
  }

  @Override
  protected boolean isClientVisible(CommandData data) {
    return true;
  }
}

package com.dcg.battle;

import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;

public class DestroyUnit extends AbstractCommandBuilder {
  private final boolean autoDestroy;

  private DestroyUnit(boolean autoDestroy) {
    this.autoDestroy = autoDestroy;
  }

  public static DestroyUnit destroyUnit() {
    return new DestroyUnit(false);
  }

  public static DestroyUnit autoDestroyUnit() {
    return new DestroyUnit(true);
  }

  @Override
  protected void run(CommandData data) {
    data.getTargets().forEach(coreSystem::remove);
  }

  @Override
  protected String getDescription(CommandData data) {
    return String.format(
        "destroys %s", coreSystem.toNames(data.getOriginEntity(), data.getTargets()));
  }

  @Override
  protected boolean isClientVisible(CommandData data) {
    return !autoDestroy;
  }
}

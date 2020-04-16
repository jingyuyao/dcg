package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.Command;

public class AddDefense extends Command {
  private final int defense;
  protected ComponentMapper<Unit> mUnit;

  public AddDefense(int defense) {
    this.defense = defense;
  }

  @Override
  protected boolean canRun() {
    return input.size() == 1 && mUnit.has(input.get(0));
  }

  @Override
  public void run() {
    mUnit.get(input.get(0)).defense += defense;
  }
}

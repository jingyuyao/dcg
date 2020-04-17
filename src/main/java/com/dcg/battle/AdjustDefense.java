package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.Command;
import com.dcg.command.CommandBase;

public class AdjustDefense extends CommandBase {
  private final int defense;
  private boolean addToSource = false;
  protected ComponentMapper<Unit> mUnit;

  public AdjustDefense(int defense) {
    this.defense = defense;
  }

  public Command toSource() {
    this.addToSource = true;
    return this;
  }

  @Override
  protected boolean isInputValid() {
    return addToSource || (input.size() == 1 && mUnit.has(input.get(0)));
  }

  @Override
  protected void run() {
    int targetEntity = addToSource ? sourceEntity : input.get(0);
    mUnit.get(targetEntity).defense += defense;
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), defense);
  }
}

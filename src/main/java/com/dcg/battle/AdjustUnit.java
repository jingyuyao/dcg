package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;

abstract class AdjustUnit extends AbstractCommandBuilder {
  private boolean addToSource = false;
  protected ComponentMapper<Unit> mUnit;

  public AdjustUnit toSource() {
    this.addToSource = true;
    return this;
  }

  @Override
  protected boolean isInputValid() {
    return addToSource || (input.size() == 1 && mUnit.has(input.get(0)));
  }

  protected int getTargetEntity() {
    return addToSource ? sourceEntity : input.get(0);
  }
}

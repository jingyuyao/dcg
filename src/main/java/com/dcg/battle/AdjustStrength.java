package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;

public class AdjustStrength extends AbstractCommandBuilder {
  private final int strength;
  private boolean addToSource;
  protected ComponentMapper<Unit> mUnit;

  public AdjustStrength(int strength) {
    this.strength = strength;
  }

  public AdjustStrength toSource() {
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
    Unit unit = mUnit.get(targetEntity);
    unit.strength += strength;
    if (unit.strength <= 0) {
      world.delete(targetEntity);
    }
  }
}

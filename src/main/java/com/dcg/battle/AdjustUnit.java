package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;

/**
 * Base command to make changes to an unit. Auto executes if the source entity is a unit, otherwise
 * accepts a single input
 */
abstract class AdjustUnit extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected boolean isInputValid() {
    return mUnit.has(sourceEntity) || (input.size() == 1 && mUnit.has(input.get(0)));
  }

  protected int getTargetEntity() {
    return mUnit.has(sourceEntity) ? sourceEntity : input.get(0);
  }
}

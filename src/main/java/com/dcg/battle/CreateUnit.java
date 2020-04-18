package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.Target;
import com.dcg.game.CreateEntity;

public class CreateUnit extends CreateEntity {
  public final int strength;
  protected ComponentMapper<Unit> mUnit;

  public CreateUnit(String name, int strength) {
    super(name);
    this.strength = strength;
    addOnEnterEffects(new Block());
  }

  @Override
  protected void run(Target target) {
    int unitEntity = createEntity(target);
    Unit unit = mUnit.create(unitEntity);
    unit.strength = strength;
  }
}

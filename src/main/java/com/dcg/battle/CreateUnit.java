package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.game.CreateEntity;

public class CreateUnit extends CreateEntity {
  public final String name;
  public final int strength;
  protected ComponentMapper<Unit> mUnit;

  public CreateUnit(String name, int strength) {
    this.name = name;
    this.strength = strength;
    addOnEnterEffects(new Block());
  }

  @Override
  protected void run() {
    int unitEntity = createEntity();
    Unit unit = mUnit.create(unitEntity);
    unit.name = name;
    unit.strength = strength;
  }
}

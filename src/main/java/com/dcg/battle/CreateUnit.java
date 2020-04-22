package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.action.CreateAction;
import com.dcg.game.CreateEntity;
import com.dcg.target.Target;

public class CreateUnit extends CreateEntity {
  public final int strength;
  protected ComponentMapper<Unit> mUnit;
  protected ComponentMapper<Defending> mDefending;

  public CreateUnit(String name, int strength) {
    super(name);
    this.strength = strength;
    addOnEnterEffects(new CreateAction(new Block()));
  }

  @Override
  protected void run(Target target) {
    int unitEntity = createEntity(target);
    Unit unit = mUnit.create(unitEntity);
    unit.strength = strength;
    mDefending.create(unitEntity);
  }
}

package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.action.CreateAction;
import com.dcg.command.CommandArgs;
import com.dcg.game.CreateEntity;
import java.util.List;

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
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    int unitEntity = createEntity(originEntity);
    Unit unit = mUnit.create(unitEntity);
    unit.strength = strength;
    unit.defense = 0;
    unit.flying = false;
    unit.lifeSteal = false;
    unit.berserk = false;
    unit.endurance = false;
    unit.unblockable = false;
    mDefending.create(unitEntity);
  }
}

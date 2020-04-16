package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.command.CommandChain;
import com.dcg.game.CreateEntity;

public class CreateUnit extends CreateEntity {
  public final String name;
  public final int strength;
  @Wire CommandChain commandChain;
  protected ComponentMapper<Unit> mUnit;

  public CreateUnit(String name, int strength) {
    this.name = name;
    this.strength = strength;
  }

  @Override
  protected void run() {
    int unitEntity = createEntity();
    Unit unit = mUnit.create(unitEntity);
    unit.name = name;
    unit.strength = strength;
    // TODO: move this into onEnter effect
    commandChain.addStart(new CreateAction(unitEntity, new Block(unitEntity)));
  }
}

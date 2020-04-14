package com.dcg.effect;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.battle.Unit;
import com.dcg.command.Command;
import com.dcg.ownership.Owned;
import com.dcg.turn.TurnSystem;

public class CreateUnit extends Command {
  public final String name;
  public final int strength;
  protected World world;
  protected TurnSystem turnSystem;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<Unit> mUnit;

  public CreateUnit(String name, int strength) {
    this.name = name;
    this.strength = strength;
  }

  @Override
  public void run() {
    int unitEntity = world.create();
    mOwned.create(unitEntity).owner = turnSystem.getCurrentPlayerEntity();
    Unit unit = mUnit.create(unitEntity);
    unit.name = name;
    unit.strength = strength;
  }
}

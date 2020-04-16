package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.ownership.Owned;
import com.dcg.turn.TurnSystem;

public class CreateUnit extends Command {
  public final String name;
  public final int strength;
  @Wire CommandChain commandChain;
  protected World world;
  protected TurnSystem turnSystem;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<Unit> mUnit;

  public CreateUnit(String name, int strength) {
    this.name = name;
    this.strength = strength;
  }

  @Override
  protected void run() {
    int unitEntity = world.create();
    mOwned.create(unitEntity).owner = turnSystem.getPlayerEntity();
    Unit unit = mUnit.create(unitEntity);
    unit.name = name;
    unit.strength = strength;
    commandChain.addStart(new CreateAction(unitEntity, new Block(unitEntity)));
  }
}

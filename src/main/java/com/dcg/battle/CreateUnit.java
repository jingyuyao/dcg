package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.effect.Effect;
import com.dcg.ownership.Owned;
import com.dcg.turn.TurnSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateUnit extends Command {
  public final String name;
  public final int strength;
  private final List<Command> onCreateEffects = new ArrayList<>();
  @Wire CommandChain commandChain;
  protected World world;
  protected TurnSystem turnSystem;
  protected ComponentMapper<Unit> mUnit;
  protected ComponentMapper<Effect> mEffect;
  protected ComponentMapper<Owned> mOwned;

  public CreateUnit(String name, int strength) {
    this.name = name;
    this.strength = strength;
  }

  // TODO: make a CreateEntity base class that has this and the owner logic.
  public CreateUnit addOnCreateEffects(Command... effects) {
    this.onCreateEffects.addAll(Arrays.asList(effects));
    return this;
  }

  @Override
  protected void run() {
    int unitEntity = world.create();
    // TODO: use command owner rather than directly using current player, units that are owned by
    // cards can be queries using getDescendants, that'll also make things more consistent,
    // Unit -> Card -> Player
    mOwned.create(unitEntity).owner = turnSystem.getPlayerEntity();
    Unit unit = mUnit.create(unitEntity);
    unit.name = name;
    unit.strength = strength;
    Effect effect = mEffect.create(unitEntity);
    effect.onCreate = onCreateEffects;
    commandChain.addStart(new CreateAction(unitEntity, new Block(unitEntity)));
  }
}

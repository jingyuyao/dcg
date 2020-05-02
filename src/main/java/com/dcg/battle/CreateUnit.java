package com.dcg.battle;

import static com.dcg.action.CreateAction.action;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandArgs;
import com.dcg.game.CreateEntity;
import com.dcg.game.Owned;
import com.dcg.game.Preconditions;
import java.util.List;

public class CreateUnit extends CreateEntity {
  public final int strength;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<Unit> mUnit;
  protected ComponentMapper<Defending> mDefending;

  public CreateUnit(String name, int strength) {
    super(name);
    this.strength = strength;
    addOnEnterEffects(action(new Block()));
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    Preconditions.checkGameState(originEntity != -1, "Must have card entity for CreateUnit");
    int ownerEntity = coreSystem.getRoot(originEntity);
    Preconditions.checkGameState(ownerEntity != -1, "Must have owner for CreateUnit");
    int unitEntity = createEntity();
    mOwned.create(unitEntity).owner = ownerEntity;
    Unit unit = mUnit.create(unitEntity);
    unit.cardEntity = originEntity;
    unit.strength = strength;
    mDefending.create(unitEntity);
  }
}

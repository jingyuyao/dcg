package com.dcg.effect;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.All;
import com.dcg.battle.Unit;
import com.dcg.location.PlayArea;
import com.dcg.ownership.Owned;

@All({PlayArea.class, Owned.class, CreateUnit.class})
public class CreateUnitSystem extends BaseEntitySystem {
  protected World world;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<CreateUnit> mCreateUnit;
  protected ComponentMapper<Unit> mUnit;

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    int unitEntity = world.create();
    mOwned.create(unitEntity).owner = mOwned.get(entityId).owner;
    CreateUnit createUnit = mCreateUnit.get(entityId);
    Unit unit = mUnit.create(unitEntity);
    unit.name = createUnit.name;
    unit.strength = createUnit.strength;
  }

  @Override
  protected void processSystem() {}
}

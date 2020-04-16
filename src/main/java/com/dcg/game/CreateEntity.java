package com.dcg.game;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.command.Command;
import com.dcg.effect.Effect;
import com.dcg.ownership.Owned;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class CreateEntity extends Command {
  protected final List<Command> onCreateEffects = new ArrayList<>();
  protected World world;
  protected ComponentMapper<Effect> mEffect;
  protected ComponentMapper<Owned> mOwned;

  public CreateEntity addOnCreateEffects(Command... effects) {
    this.onCreateEffects.addAll(Arrays.asList(effects));
    return this;
  }

  protected int createEntity() {
    int unitEntity = world.create();
    if (owner != -1) {
      Owned owned = mOwned.create(unitEntity);
      owned.owner = owner;
    }
    if (!onCreateEffects.isEmpty()) {
      Effect effect = mEffect.create(unitEntity);
      effect.onCreate = onCreateEffects;
    }
    return unitEntity;
  }
}

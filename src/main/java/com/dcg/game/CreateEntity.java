package com.dcg.game;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.action.DeleteActions;
import com.dcg.command.Command;
import com.dcg.command.CommandBase;
import com.dcg.effect.Effect;
import com.dcg.ownership.Owned;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Base class for commands that create entities. Automatically propagates owner of the command to
 * the created entity if available and automatically adds an onLeave effect to cleanup any actions
 * tied to the created entity.
 */
public abstract class CreateEntity extends CommandBase {
  protected final List<Command> onEnterEffects = new ArrayList<>();
  protected final List<Command> onLeaveEffects = new ArrayList<>();
  protected World world;
  protected ComponentMapper<Effect> mEffect;
  protected ComponentMapper<Owned> mOwned;

  public CreateEntity() {
    addOnLeaveEffects(new DeleteActions());
  }

  public Command addOnEnterEffects(Command... effects) {
    this.onEnterEffects.addAll(Arrays.asList(effects));
    return this;
  }

  public Command addOnLeaveEffects(Command... effects) {
    this.onLeaveEffects.addAll(Arrays.asList(effects));
    return this;
  }

  protected int createEntity() {
    int unitEntity = world.create();
    if (owner != -1) {
      Owned owned = mOwned.create(unitEntity);
      owned.owner = owner;
    }
    Effect effect = mEffect.create(unitEntity);
    effect.onEnter = onEnterEffects;
    effect.onLeave = onLeaveEffects;
    return unitEntity;
  }
}

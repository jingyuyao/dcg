package com.dcg.game;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.action.DeleteActions;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.effect.Effect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Base class for commands that create entities. Automatically propagates owner of the command to
 * the created entity if available and automatically adds an onLeave effect to cleanup any actions
 * tied to the created entity.
 */
public abstract class CreateEntity extends AbstractCommandBuilder {
  protected final List<CommandBuilder> onEnterEffects = new ArrayList<>();
  protected final List<CommandBuilder> onLeaveEffects = new ArrayList<>();
  protected final List<CommandBuilder> onConditionEffects = new ArrayList<>();
  protected World world;
  protected ComponentMapper<Effect> mEffect;
  protected ComponentMapper<Owned> mOwned;

  public CreateEntity() {
    addOnLeaveEffects(new DeleteActions());
  }

  public CreateEntity addOnEnterEffects(CommandBuilder... effects) {
    this.onEnterEffects.addAll(Arrays.asList(effects));
    return this;
  }

  public CreateEntity addOnLeaveEffects(CommandBuilder... effects) {
    this.onLeaveEffects.addAll(Arrays.asList(effects));
    return this;
  }

  public CreateEntity addOnConditionEffects(CommandBuilder... effects) {
    this.onConditionEffects.addAll(Arrays.asList(effects));
    return this;
  }

  protected int createEntity() {
    int unitEntity = world.create();
    if (sourceEntity != -1) {
      Owned owned = mOwned.create(unitEntity);
      owned.owner = sourceEntity;
    }
    Effect effect = mEffect.create(unitEntity);
    effect.onEnter = onEnterEffects;
    effect.onLeave = onLeaveEffects;
    effect.onCondition = onConditionEffects;
    return unitEntity;
  }
}

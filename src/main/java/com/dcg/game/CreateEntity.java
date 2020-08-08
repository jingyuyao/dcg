package com.dcg.game;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.action.RemoveActions;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.effect.Effect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Base class for commands that create entities. Automatically propagates owner of the command to
 * the created entity if available and automatically adds an onLeave effect to cleanup any actions
 * tied to the created entity.
 */
public abstract class CreateEntity extends AbstractCommandBuilder {
  protected final String name;
  protected String description;
  protected World world;
  protected ComponentMapper<Active> mActive;
  protected ComponentMapper<Common> mCommon;
  protected ComponentMapper<Effect> mEffect;
  private final List<Class<? extends Component>> tags = new ArrayList<>();
  private final List<CommandBuilder> onEnterEffects = new ArrayList<>();
  private final List<CommandBuilder> onLeaveEffects = new ArrayList<>();
  private final List<CommandBuilder> onConditionEffects = new ArrayList<>();

  public CreateEntity(String name) {
    this.name = name;
    addOnLeaveEffects(new RemoveActions());
  }

  public CreateEntity desc(String description) {
    this.description = description;
    return this;
  }

  @SafeVarargs
  public final CreateEntity tags(Class<? extends Component>... tags) {
    Collections.addAll(this.tags, tags);
    return this;
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
    int entity = world.create();
    mActive.create(entity);
    Common common = mCommon.create(entity);
    common.name = name;
    if (description != null) {
      common.description = description;
    }
    for (Class<? extends Component> tag : tags) {
      world.getMapper(tag).create(entity);
    }
    Effect effect = mEffect.create(entity);
    effect.onEnter = onEnterEffects;
    effect.onLeave = onLeaveEffects;
    effect.onCondition = onConditionEffects;
    return entity;
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), name);
  }
}

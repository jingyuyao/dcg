package com.dcg.game;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.action.DeleteActions;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.effect.Effect;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.OptionalInt;

/**
 * Base class for commands that create entities. Automatically propagates owner of the command to
 * the created entity if available and automatically adds an onLeave effect to cleanup any actions
 * tied to the created entity.
 */
public abstract class CreateEntity extends AbstractCommandBuilder {
  private final String name;
  private String description;
  private final List<Class<? extends Component>> tags = new ArrayList<>();
  private final List<CommandBuilder> onEnterEffects = new ArrayList<>();
  private final List<CommandBuilder> onLeaveEffects = new ArrayList<>();
  private final List<CommandBuilder> onConditionEffects = new ArrayList<>();
  protected World world;
  protected ComponentMapper<Common> mCommon;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<Effect> mEffect;

  public CreateEntity(String name) {
    this.name = name;
    addOnLeaveEffects(new DeleteActions());
  }

  public CreateEntity desc(String description) {
    this.description = description;
    return this;
  }

  public CreateEntity addTag(Class<? extends Component> tag) {
    this.tags.add(tag);
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

  protected int createEntity(int originEntity) {
    int entity = world.create();
    Common common = mCommon.create(entity);
    common.name = name;
    if (description != null) {
      common.description = description;
    }
    getOwner(originEntity)
        .ifPresent(
            ownerEntity -> {
              Owned owned = mOwned.create(entity);
              owned.owner = ownerEntity;
            });
    for (Class<? extends Component> tag : tags) {
      world.getMapper(tag).create(entity);
    }
    Effect effect = mEffect.create(entity);
    effect.onEnter = onEnterEffects;
    effect.onLeave = onLeaveEffects;
    effect.onCondition = onConditionEffects;
    return entity;
  }

  protected OptionalInt getOwner(int originEntity) {
    return originEntity == -1 ? OptionalInt.empty() : OptionalInt.of(originEntity);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), name);
  }
}

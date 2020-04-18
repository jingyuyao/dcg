package com.dcg.game;

import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.dcg.action.DeleteActions;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.command.Target;
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
  private final List<Class<? extends Component>> tags = new ArrayList<>();
  private final List<CommandBuilder> onEnterEffects = new ArrayList<>();
  private final List<CommandBuilder> onLeaveEffects = new ArrayList<>();
  private final List<CommandBuilder> onConditionEffects = new ArrayList<>();
  protected World world;
  protected ComponentMapper<Effect> mEffect;
  protected ComponentMapper<Owned> mOwned;

  public CreateEntity() {
    addOnLeaveEffects(new DeleteActions());
  }

  public CreateEntity addTags(List<Class<? extends Component>> tags) {
    // NOTE: Needs to take a list because of generics
    this.tags.addAll(tags);
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

  protected int createEntity(Target target) {
    int entity = world.create();
    getOwner(target)
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

  protected OptionalInt getOwner(Target target) {
    int owner = target.get().get(0);
    return owner == -1 ? OptionalInt.empty() : OptionalInt.of(owner);
  }
}

package com.dcg.debug;

import com.artemis.ComponentMapper;
import com.dcg.action.Action;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandBuilder;
import com.dcg.effect.Effect;

public abstract class DebugEntityCommand extends AbstractCommandBuilder {
  protected ComponentMapper<Action> mAction;
  protected ComponentMapper<Effect> mEffect;

  protected String name(int entity) {
    return coreSystem.toName(entity);
  }

  protected void printEffects(int entity) {
    if (mEffect.has(entity)) {
      Effect effect = mEffect.get(entity);
      for (CommandBuilder builder : effect.onEnter) {
        System.out.printf("      %s\n", builder.build(world, entity));
      }
      for (CommandBuilder builder : effect.onCondition) {
        System.out.printf("      %s\n", builder.build(world, entity));
      }
    }
  }

  protected void printAction(int actionEntity) {
    Action action = mAction.get(actionEntity);
    System.out.printf("    -- *%d %s\n", actionEntity, action);
  }
}

package com.dcg.effect;

import static com.dcg.action.CreateAction.action;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.One;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.dcg.battle.Defending;
import com.dcg.command.Command;
import com.dcg.command.CommandBuilder;
import com.dcg.command.CommandChain;
import com.dcg.game.CoreSystem;
import com.dcg.location.PlayArea;
import com.dcg.turn.Turn;
import java.util.List;

/**
 * System to trigger effects. Effects are only triggered when entities enter and leave certain
 * activation tags such as Turn, PlayArea or they are just plainly created.
 */
@All({Effect.class})
@One({Turn.class, PlayArea.class, Defending.class})
public class EffectSystem extends IteratingSystem {
  @Wire protected CommandChain commandChain;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Effect> mEffect;

  @Override
  protected void inserted(int entityId) {
    triggerEffects(entityId, mEffect.get(entityId).onEnter);
  }

  @Override
  protected void removed(int entityId) {
    if (mEffect.has(entityId)) {
      triggerEffects(entityId, mEffect.get(entityId).onLeave);
    }
  }

  @Override
  protected void process(int entityId) {
    Turn turn = coreSystem.getTurn();
    Effect effect = mEffect.get(entityId);
    effect.onCondition.stream()
        .filter(builder -> !turn.triggeredConditionalEffects.contains(builder))
        .forEach(
            builder -> {
              Command command = builder.build(world, entityId);
              if (command.canTrigger()) {
                if (command.isInputValid()) {
                  System.out.printf("Effect: all conditions valid for %s\n", command);
                  commandChain.addEnd(command);
                } else {
                  System.out.printf("Effect: trigger conditions valid for %s\n", command);
                  // TODO: how to pass good names here?
                  commandChain.addEnd(action(builder).build(world, entityId));
                }
                turn.triggeredConditionalEffects.add(builder);
              }
            });
  }

  private void triggerEffects(int entityId, List<CommandBuilder> effects) {
    for (CommandBuilder builder : effects) {
      Command command = builder.build(world, entityId);
      if (command.canRun()) {
        commandChain.addEnd(command);
      } else {
        commandChain.addEnd(action(builder).build(world, entityId));
      }
    }
  }
}

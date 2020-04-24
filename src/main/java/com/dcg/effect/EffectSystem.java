package com.dcg.effect;

import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.One;
import com.artemis.annotations.Wire;
import com.artemis.systems.IteratingSystem;
import com.dcg.action.CreateAction;
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
@All(Effect.class)
@One({Turn.class, PlayArea.class, Defending.class})
public class EffectSystem extends IteratingSystem {
  @Wire protected CommandChain commandChain;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Effect> mEffect;
  protected ComponentMapper<Turn> mTurn;

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
    coreSystem
        .getCurrentPlayerEntity()
        .map(mTurn::get)
        .forEach(
            turn -> {
              Effect effect = mEffect.get(entityId);
              effect.onCondition.stream()
                  .filter(builder -> !turn.triggeredConditionalEffects.contains(builder))
                  .forEach(
                      builder -> {
                        Command command = builder.build(world, entityId);
                        if (command.canTrigger()) {
                          System.out.printf("World condition met for %s\n", command);
                          if (command.isInputValid()) {
                            System.out.printf("Input condition valid for %s\n", command);
                            commandChain.addEnd(command);
                          } else {
                            System.out.printf("Creating action for %s\n", command);
                            commandChain.addEnd(new CreateAction(builder).build(world, entityId));
                          }
                          turn.triggeredConditionalEffects.add(builder);
                        }
                      });
            });
  }

  private void triggerEffects(int entityId, List<CommandBuilder> effects) {
    for (CommandBuilder builder : effects) {
      Command command = builder.build(world, entityId);
      if (command.canRun()) {
        commandChain.addEnd(command);
      } else {
        commandChain.addEnd(new CreateAction(builder).build(world, entityId));
      }
    }
  }
}

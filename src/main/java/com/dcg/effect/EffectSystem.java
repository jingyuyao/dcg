package com.dcg.effect;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.One;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.battle.Unit;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.command.ExecutableCommand;
import com.dcg.location.PlayArea;
import com.dcg.player.Turn;
import java.util.List;

/**
 * System to trigger effects. Effects are only triggered when entities enter and leave certain
 * activation tags such as Turn, PlayArea or they are just plainly created.
 */
@All(Effect.class)
@One({Turn.class, PlayArea.class, Unit.class})
public class EffectSystem extends BaseEntitySystem {
  @Wire protected CommandChain commandChain;
  protected ComponentMapper<Effect> mEffect;

  @Override
  protected void inserted(int entityId) {
    triggerEffects(entityId, mEffect.get(entityId).onEnter);
  }

  @Override
  protected void removed(int entityId) {
    triggerEffects(entityId, mEffect.get(entityId).onLeave);
  }

  @Override
  protected void processSystem() {}

  private void triggerEffects(int entityId, List<Command> effects) {
    for (Command command : effects) {
      ExecutableCommand executableCommand = command.build(world, entityId);
      if (executableCommand.canRun()) {
        commandChain.addEnd(executableCommand);
      } else {
        commandChain.addEnd(new CreateAction(command).build(world, entityId));
      }
    }
  }
}

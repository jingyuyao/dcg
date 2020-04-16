package com.dcg.effect;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.All;
import com.artemis.annotations.One;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.battle.Unit;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.PlayArea;
import com.dcg.turn.Turn;

@All(Effect.class)
@One({Turn.class, PlayArea.class, Unit.class})
public class EffectSystem extends BaseEntitySystem {
  @Wire protected CommandChain commandChain;
  protected World world;
  protected ComponentMapper<Effect> mEffect;

  @Override
  protected void inserted(int entityId) {
    Effect effect = mEffect.get(entityId);
    for (Command command : effect.onEnter) {
      command.setOwner(entityId);
      if (command.canRun(world)) {
        commandChain.addEnd(command);
      } else {
        commandChain.addEnd(new CreateAction(command).setOwner(entityId));
      }
    }
  }

  @Override
  protected void processSystem() {}
}

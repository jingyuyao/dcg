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

// TODO: make AdvanceTurn or draw card an effect of player going into turn?
// damn this is opening up so many possibilities
@All(Effect.class)
@One({PlayArea.class, Unit.class})
public class EffectSystem extends BaseEntitySystem {
  @Wire CommandChain commandChain;
  protected World world;
  protected ComponentMapper<Effect> mEffect;

  @Override
  protected void inserted(int entityId) {
    Effect effect = mEffect.get(entityId);
    // Looping in reverse so the effects are added to the chain in the correct order.
    for (int i = effect.onCreate.size() - 1; i >= 0; i--) {
      Command command = effect.onCreate.get(i);
      if (command.canRun(world)) {
        command.setOwner(entityId);
        commandChain.addStart(command);
      } else {
        commandChain.addStart(new CreateAction(entityId, command));
      }
    }
  }

  @Override
  protected void processSystem() {}
}

package com.dcg.forge;

import com.artemis.annotations.Wire;
import com.dcg.battle.CreateUnit;
import com.dcg.card.CreateCard;
import com.dcg.command.CommandBase;
import com.dcg.command.CommandChain;

public class InitializeForge extends CommandBase {
  @Wire protected CommandChain commandChain;

  @Override
  protected void run() {
    for (int i = 0; i < 50; i++) {
      commandChain.addEnd(
          new CreateCard("Yeti", 2).addOnEnterEffects(new CreateUnit("Yeti", 2)).build(world, -1));
    }
    for (int i = 0; i < 6; i++) {
      commandChain.addEnd(new DrawFromForge().build(world, -1));
    }
  }
}

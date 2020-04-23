package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.EffectValueSupplier;
import com.dcg.target.Target;

public class AdjustHp extends PlayerEffect {
  protected ComponentMapper<Player> mPlayer;

  public AdjustHp(int hp) {
    setEffectValueSupplier(() -> hp);
  }

  public AdjustHp(EffectValueSupplier effectValueSupplier) {
    setEffectValueSupplier(effectValueSupplier);
  }

  @Override
  protected void run(Target target) {
    for (int playerEntity : target.getTargets()) {
      Player player = mPlayer.get(playerEntity);
      player.hp += getEffectValue();
      if (player.hp <= 0) {
        commandChain.addEnd(new DeletePlayer().build(world, playerEntity));
      }
    }
  }
  // TODO: how to display hp in logs again? don't use tostring and use logger directly?
}

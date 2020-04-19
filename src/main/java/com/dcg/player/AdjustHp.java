package com.dcg.player;

import com.dcg.command.Target;
import com.dcg.effect.EffectValueSupplier;

public class AdjustHp extends PlayerEffect {
  public AdjustHp(int hp) {
    setEffectValueSupplier(() -> hp);
  }

  public AdjustHp(EffectValueSupplier effectValueSupplier) {
    setEffectValueSupplier(effectValueSupplier);
  }

  @Override
  protected void run(Target target) {
    for (int playerEntity : target.getTo()) {
      Player player = mPlayer.get(playerEntity);
      player.hp += getEffectValue();
      if (player.hp <= 0) {
        commandChain.addEnd(new DeletePlayer().build(world, playerEntity));
      }
    }
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), getEffectValue());
  }
}

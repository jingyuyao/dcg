package com.dcg.player;

import com.dcg.command.Target;
import com.dcg.source.EffectSource;
import java.util.Collections;

public class AdjustHp extends PlayerEffect {
  public AdjustHp(int hp) {
    setEffectSource(() -> () -> Collections.singletonList(hp));
  }

  public AdjustHp(EffectSource effectSource) {
    setEffectSource(effectSource);
  }

  @Override
  protected void run(Target target) {
    target
        .get()
        .forEach(
            playerEntity -> {
              Player player = mPlayer.get(playerEntity);
              player.hp += getHp();
              if (player.hp <= 0) {
                commandChain.addEnd(new DeletePlayer().build(world, playerEntity));
              }
            });
  }

  private int getHp() {
    return getEffectSource().get().stream().mapToInt(Integer::intValue).sum();
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), getHp());
  }
}

package com.dcg.player;

import java.util.List;

public class AdjustHp extends PlayerEffect {
  private final int hp;

  public AdjustHp(int hp) {
    this.hp = hp;
  }

  @Override
  protected void run(List<Integer> input) {
    getTargetEntities(input)
        .forEach(
            playerEntity -> {
              Player player = mPlayer.get(playerEntity);
              player.hp += hp;
              if (player.hp <= 0) {
                commandChain.addEnd(new DeletePlayer().build(world, playerEntity));
              }
            });
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), hp);
  }
}

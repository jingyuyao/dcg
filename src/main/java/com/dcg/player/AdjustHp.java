package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;

public class AdjustHp extends AbstractEffectBuilder<Player> {
  private final int hp;
  protected ComponentMapper<Player> mPlayer;

  public AdjustHp(int hp) {
    this.hp = hp;
  }

  @Override
  protected void run() {
    // TODO: game over should be checked here.
    getTargetComponents().forEach(player -> player.hp += hp);
  }

  @Override
  protected ComponentMapper<Player> getComponentMapper() {
    return mPlayer;
  }

  @Override
  protected int transformTargetEntity(int targetEntity) {
    return coreSystem.getRoot(targetEntity);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), hp);
  }
}

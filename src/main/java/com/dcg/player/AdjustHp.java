package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.effect.AbstractEffectBuilder;
import java.util.List;

public class AdjustHp extends AbstractEffectBuilder<Player> {
  private final int hp;
  // Whether to automatically use the root of target entities. If false this command will only
  // trigger if attached has a source entity of player or has a player entity input.
  private final boolean crawlRoot;
  protected ComponentMapper<Player> mPlayer;

  public AdjustHp(int hp, boolean crawlRoot) {
    this.hp = hp;
    this.crawlRoot = crawlRoot;
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
  protected ComponentMapper<Player> getComponentMapper() {
    return mPlayer;
  }

  @Override
  protected int transformTargetEntity(int targetEntity) {
    return crawlRoot ? coreSystem.getRoot(targetEntity) : targetEntity;
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), hp);
  }
}

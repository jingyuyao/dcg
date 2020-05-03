package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandArgs;
import java.util.List;
import java.util.function.Supplier;

public class AdjustHp extends PlayerEffect {
  protected ComponentMapper<Player> mPlayer;

  private AdjustHp(int hp) {
    setIntArgSupplier(() -> hp);
  }

  public AdjustHp(Supplier<Integer> hpSupplier) {
    setIntArgSupplier(hpSupplier);
  }

  public static AdjustHp hp(int hp) {
    return new AdjustHp(hp);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    for (int playerEntity : targets) {
      Player player = mPlayer.get(playerEntity);
      player.hp += args.getInt();
    }
  }
}

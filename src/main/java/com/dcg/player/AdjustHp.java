package com.dcg.player;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandData;
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
  protected void run(CommandData data) {
    for (int playerEntity : data.getTargets()) {
      Player player = mPlayer.get(playerEntity);
      player.hp += data.getInt();
    }
  }
}

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

  @Override
  protected String getDescription(CommandData data) {
    int value = data.getInt();
    String targetNames = coreSystem.toNames(data.getTargets());
    return value > 0
        ? String.format("heals %s by %d HP", targetNames, value)
        : String.format("deals %d damage to %s", -value, targetNames);
  }

  @Override
  protected boolean isClientVisible(CommandData data) {
    return true;
  }
}

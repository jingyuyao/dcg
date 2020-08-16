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
    String originOwnerName = coreSystem.toName(coreSystem.getRoot(data.getOriginEntity()));
    String targetNames = coreSystem.toNames(data.getOriginEntity(), data.getTargets());
    return value > 0
        ? String.format("heals %d HP to %s by %s", value, targetNames, originOwnerName)
        : String.format("deals %d damage to %s by %s", -value, targetNames, originOwnerName);
  }

  @Override
  protected boolean isClientVisible(CommandData data) {
    return true;
  }
}

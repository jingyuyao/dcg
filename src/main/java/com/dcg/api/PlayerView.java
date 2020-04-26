package com.dcg.api;

import com.dcg.game.Common;
import com.dcg.player.Player;
import com.dcg.turn.Turn;
import java.util.List;

public class PlayerView extends EntityView {
  public final int hp;
  public final int warpTokens;
  public final boolean isCurrent;
  public final int powerPool;
  public final List<ActionView> actions;

  public PlayerView(int id, Common common, Player player, Turn turn, List<ActionView> actions) {
    super(id, common);
    this.hp = player.hp;
    this.warpTokens = player.flashTokens;
    this.isCurrent = turn != null;
    this.powerPool = turn != null ? turn.powerPool : 0;
    this.actions = actions;
  }
}

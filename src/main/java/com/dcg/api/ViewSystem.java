package com.dcg.api;

import com.artemis.Aspect;
import com.artemis.BaseSystem;
import com.artemis.ComponentMapper;
import com.dcg.action.Action;
import com.dcg.game.Common;
import com.dcg.game.CoreSystem;
import com.dcg.player.Player;
import com.dcg.turn.Turn;
import java.util.List;
import java.util.stream.Collectors;

/** Provide views into the game world. */
public class ViewSystem extends BaseSystem {
  protected CoreSystem coreSystem;
  protected ComponentMapper<Common> mCommon;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Turn> mTurn;
  protected ComponentMapper<Action> mAction;

  public WorldView getWorldView() {
    return new WorldView(getPlayers());
  }

  private List<PlayerView> getPlayers() {
    return coreSystem
        .getStream(Aspect.all(Player.class))
        .map(
            playerEntity -> {
              Common common = mCommon.get(playerEntity);
              Player player = mPlayer.get(playerEntity);
              Turn turn = mTurn.has(playerEntity) ? mTurn.get(playerEntity) : null;
              List<ActionView> actions = getActions(playerEntity);
              return new PlayerView(playerEntity, common, player, turn, actions);
            })
        .collect(Collectors.toList());
  }

  private List<ActionView> getActions(int ownerEntity) {
    return coreSystem
        .getChildren(ownerEntity, Aspect.all(Action.class))
        .map(
            actionEntity -> {
              Common common = mCommon.get(actionEntity);
              Action action = mAction.get(actionEntity);
              return new ActionView(actionEntity, common, action);
            })
        .collect(Collectors.toList());
  }

  @Override
  protected void processSystem() {}
}

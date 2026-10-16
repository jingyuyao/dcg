package com.dcg.action;

import com.dcg.api.GameView;
import com.dcg.game.Game;
import java.util.Arrays;
import org.junit.Test;

public class ExecuteActionTest {
  @Test
  public void invalidActionIdsAreIgnored() {
    Game game = new Game(Arrays.asList("A", "B"));
    GameView view = game.getGameView("A");
    String currentPlayerName =
        view.players.stream().filter(p -> p.isCurrent).findFirst().get().name;
    // A player entity is not an action, and the other ids do not exist.
    for (Integer id : Arrays.asList(view.currentPlayerId, -5, 999999, null)) {
      game.execute(currentPlayerName, Arrays.asList(id));
    }
    game.execute(currentPlayerName, Arrays.asList(view.currentPlayerId, (Integer) null));
  }
}

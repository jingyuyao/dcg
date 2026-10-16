package com.dcg.game;

import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import org.junit.Test;

public class PlayerNameTest {
  @Test
  public void namesThatOnlyDifferByCaseAreDifferentPlayers() {
    Game game = new Game(Arrays.asList("Bob", "bob"));
    assertNotEquals(game.getGameView("Bob").playerId, game.getGameView("bob").playerId);
  }
}

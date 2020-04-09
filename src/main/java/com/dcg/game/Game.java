package com.dcg.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.dcg.command.Command;
import com.dcg.command.CommandDeque;
import com.dcg.debug.PlayerDebugSystem;
import com.dcg.forge.BuyPileRefillSystem;
import com.dcg.forge.InitializeDrawPile;
import com.dcg.player.CreatePlayer;
import com.dcg.player.PlayerTurnSystem;
import com.dcg.turn.AdvanceTurn;
import com.dcg.turn.InitTurn;

public class Game {

  private final WorldConfiguration configuration =
      new WorldConfigurationBuilder()
          // Allows for component inspection during removed()
          .alwaysDelayComponentRemoval(true)
          // Uses the command pattern for execution
          .register(new CommandInvocationStrategy())
          // Order matters!
          .with(new BuyPileRefillSystem(), new PlayerTurnSystem(), new PlayerDebugSystem())
          .build()
          .register(new CommandDeque());
  private final World world = new World(configuration);
  private boolean gameOver = false;

  public Game() {
    process(new InitializeDrawPile());
    process(new CreatePlayer("Alice"));
    process(new CreatePlayer("Bob"));
    process(new InitTurn("Alice"));
  }

  public void handleInput(String input) {
    if (input == null) return;

    switch (input) {
      case "quit":
        gameOver = true;
        break;
      case "advance":
        process(new AdvanceTurn());
        break;
    }
  }

  public boolean isOver() {
    return gameOver;
  }

  private void process(Command command) {
    world.getRegistered(CommandDeque.class).addLast(command);
    world.process();
    world.getSystem(PlayerDebugSystem.class).printPlayers();
  }
}

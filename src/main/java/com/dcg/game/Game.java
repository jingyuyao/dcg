package com.dcg.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.dcg.command.Command;
import com.dcg.command.CommandQueue;
import com.dcg.debug.DebugSystem;
import com.dcg.player.AddPlayer;
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
          .with(new PlayerTurnSystem(), new DebugSystem())
          .build()
          .register(new CommandQueue());
  private final World world = new World(configuration);
  private boolean gameOver = false;

  public Game() {
    process(new AddPlayer("Alice"), new AddPlayer("Bob"), new InitTurn("Alice"));
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

  private void process(Command... commands) {
    world.getRegistered(CommandQueue.class).add(commands);
    world.process();
  }
}

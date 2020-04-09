package com.dcg.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.link.EntityLinkManager;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.debug.PrintCurrentActions;
import com.dcg.debug.PrintCurrentPlayer;
import com.dcg.forge.ForgeRowRefillSystem;
import com.dcg.forge.InitializeForgeDeck;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.CreatePlayer;
import com.dcg.player.PerformActions;
import com.dcg.player.PlayerActionSystem;
import com.dcg.turn.InitTurn;
import com.dcg.turn.TurnSystem;

public class Game {

  private final WorldConfiguration configuration =
      new WorldConfigurationBuilder()
          // Allows for component inspection during removed()
          .alwaysDelayComponentRemoval(true)
          // Uses the command pattern for execution
          .register(new CommandInvocationStrategy())
          // Order matters!
          .with(
              new EntityLinkManager(),
              new OwnershipSystem(),
              new ForgeRowRefillSystem(),
              new TurnSystem(),
              new PlayerActionSystem())
          .build()
          .register(new CommandChain());
  private final World world = new World(configuration);
  private boolean gameOver = false;

  public Game() {
    process(
        new InitializeForgeDeck(),
        new CreatePlayer("Alice"),
        new CreatePlayer("Bob"),
        new InitTurn("Alice"));
  }

  public void handleMessage(Message message) {
    switch (message.getType()) {
      case QUIT:
        gameOver = true;
        break;
      case PRINT:
        process(new PrintCurrentPlayer(), new PrintCurrentActions());
        break;
      case PERFORM:
        process(new PerformActions(message.getIntegerArgs().toArray(new Integer[0])));
        break;
      default:
        System.out.println("Unsupported input: " + message.getType());
        break;
    }
  }

  public boolean isOver() {
    return gameOver;
  }

  private void process(Command... commands) {
    world.getRegistered(CommandChain.class).addEnd(commands);
    world.process();
  }
}

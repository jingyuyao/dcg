package com.dcg.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.link.EntityLinkManager;
import com.dcg.Input;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.debug.PlayerDebugSystem;
import com.dcg.forge.ForgeRowRefillSystem;
import com.dcg.forge.InitializeForgeDeck;
import com.dcg.player.CreatePlayer;
import com.dcg.player.CurrentPlayerActions;
import com.dcg.player.PlayerOwnedSystem;
import com.dcg.player.PlayerTurnSystem;
import com.dcg.turn.InitTurn;

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
              new ForgeRowRefillSystem(),
              new PlayerTurnSystem(),
              new PlayerOwnedSystem(),
              new PlayerDebugSystem())
          .build()
          .register(new CommandChain());
  private final World world = new World(configuration);
  private boolean gameOver = false;

  public Game() {
    process(new InitializeForgeDeck());
    process(new CreatePlayer("Alice"));
    process(new CreatePlayer("Bob"));
    process(new InitTurn("Alice"));
  }

  public void handleInput(Input input) {
    if (input.quit) {
      gameOver = true;
    } else if (input.print) {
      // TODO: make this a command
      world.getSystem(PlayerDebugSystem.class).printPlayers();
      CurrentPlayerActions query = new CurrentPlayerActions();
      process(query);
      for (int i = 0; i < query.getCommands().size(); i++) {
        System.out.println(i + " " + query.getCommands().get(i));
      }
    } else if (input.choose != -1) {
      CurrentPlayerActions query = new CurrentPlayerActions();
      process(query);
      try {
        process(query.getCommands().get(input.choose));
      } catch (ArrayIndexOutOfBoundsException e) {
        System.out.println("unknown action: " + input.choose);
      }
    } else {
      System.out.println("unknown parameters: " + input.parameters);
    }
  }

  public boolean isOver() {
    return gameOver;
  }

  private void process(Command command) {
    world.getRegistered(CommandChain.class).addEnd(command);
    world.process();
  }
}

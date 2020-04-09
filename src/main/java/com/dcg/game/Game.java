package com.dcg.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.link.EntityLinkManager;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.debug.PlayerDebugSystem;
import com.dcg.forge.ForgeRowRefillSystem;
import com.dcg.forge.InitializeForgeDeck;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.CreatePlayer;
import com.dcg.player.CurrentPlayerActions;
import com.dcg.player.PlayerTurnSystem;
import com.dcg.turn.InitTurn;
import java.util.List;

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
              new OwnershipSystem(),
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

  public void handleMessage(Message message) {
    switch (message.getType()) {
      case QUIT:
        gameOver = true;
        break;
      case PLAYERS:
        printPlayers();
        break;
      case CHOOSE:
        choose(message.getIntegerArgs());
        break;
      default:
        System.out.println("Unsupported input: " + message.getType());
        break;
    }
  }

  public boolean isOver() {
    return gameOver;
  }

  private void printPlayers() {
    // TODO: make this a command
    world.getSystem(PlayerDebugSystem.class).printPlayers();
    CurrentPlayerActions query = new CurrentPlayerActions();
    process(query);
    for (int i = 0; i < query.getCommands().size(); i++) {
      System.out.println(i + " " + query.getCommands().get(i));
    }
  }

  private void choose(List<Integer> choices) {
    CurrentPlayerActions query = new CurrentPlayerActions();
    process(query);
    for (int choice : choices) {
      try {
        process(query.getCommands().get(choice));
      } catch (IndexOutOfBoundsException e) {
        System.out.println("Unknown choice: " + choice);
      }
    }
  }

  private void process(Command command) {
    world.getRegistered(CommandChain.class).addEnd(command);
    world.process();
  }
}

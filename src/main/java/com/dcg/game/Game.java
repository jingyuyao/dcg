package com.dcg.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.link.EntityLinkManager;
import com.dcg.battle.EnterBattleSystem;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.command.CommandInvocationStrategy;
import com.dcg.debug.PrintCurrentActions;
import com.dcg.debug.PrintCurrentPlayer;
import com.dcg.forge.ForgeRowRefillSystem;
import com.dcg.forge.InitializeForgeDeck;
import com.dcg.game.Message.MessageType;
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
              new EnterBattleSystem(),
              new GameOverSystem(),
              new PlayerActionSystem())
          .build()
          .register(new CommandChain());
  private final World world = new World(configuration);

  public Game() {
    process(
        new InitializeForgeDeck(),
        new CreatePlayer("Alice"),
        new CreatePlayer("Bob"),
        new InitTurn("Alice"));
  }

  public void handleMessage(Message message) {
    if (message.getType() == MessageType.PERFORM) {
      process(new PerformActions(message.getIntegerArgs()));
    } else {
      System.out.println("Unsupported input: " + message.getType());
    }
  }

  public boolean isOver() {
    return world.getSystem(GameOverSystem.class).isOver();
  }

  private void process(Command... commands) {
    CommandChain commandChain = world.getRegistered(CommandChain.class);
    commandChain.addEnd(commands);
    world.process();

    commandChain.addEnd(new PrintCurrentPlayer(), new PrintCurrentActions());
    world.process();
  }
}

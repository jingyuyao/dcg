package com.dcg.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.link.EntityLinkManager;
import com.dcg.battle.EnterBattleSystem;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.command.CommandInvocationStrategy;
import com.dcg.debug.PrintBattleArea;
import com.dcg.debug.PrintCurrentActions;
import com.dcg.debug.PrintPlayers;
import com.dcg.forge.ForgeRowRefillSystem;
import com.dcg.forge.InitializeForgeDeck;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.CreatePlayer;
import com.dcg.player.PerformAction;
import com.dcg.player.PlayerActionSystem;
import com.dcg.turn.InitTurn;
import com.dcg.turn.TurnSystem;
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
        new CreatePlayer("Charlie"),
        new InitTurn("Alice"));
  }

  public void handleInput(List<Integer> input) {
    if (input.size() == 0) {
      System.out.println("No input");
      return;
    }
    PerformAction performAction = new PerformAction(input.get(0));
    performAction.setArgs(input.subList(1, input.size()));
    process(performAction);
  }

  public boolean isOver() {
    return world.getSystem(GameOverSystem.class).isOver();
  }

  private void process(Command... commands) {
    CommandChain commandChain = world.getRegistered(CommandChain.class);
    commandChain.addEnd(commands);
    world.process();

    commandChain.addEnd(new PrintPlayers(), new PrintBattleArea(), new PrintCurrentActions());
    world.process();
  }
}

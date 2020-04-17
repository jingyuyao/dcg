package com.dcg.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.link.EntityLinkManager;
import com.dcg.action.ExecuteAction;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.command.CommandInvocationStrategy;
import com.dcg.debug.PrintCurrentPlayerHand;
import com.dcg.debug.PrintForgeRow;
import com.dcg.debug.PrintPlayArea;
import com.dcg.debug.PrintPlayers;
import com.dcg.debug.PrintUnits;
import com.dcg.effect.EffectSystem;
import com.dcg.forge.InitializeForge;
import com.dcg.ownership.OwnershipSystem;
import com.dcg.player.CreatePlayer;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
              new AspectSystem(),
              new OwnershipSystem(),
              new EffectSystem(),
              new GameOverSystem())
          .build()
          .register(new Random())
          .register(new CommandChain());
  private final World world = new World(configuration);

  public Game() {
    process(new InitializeForge());
    process(new CreatePlayer("Edelgard"));
    process(new CreatePlayer("Dimitri"));
    process(new CreatePlayer("Claude"));
    process(new InitTurn("Edelgard"));
  }

  public void handleInput(List<Integer> input) {
    if (input.size() == 0) {
      System.out.println("No input");
      return;
    }
    process(new ExecuteAction(), input);
  }

  public boolean isOver() {
    return world.getSystem(GameOverSystem.class).isOver();
  }

  private void process(Command command) {
    process(command, Collections.emptyList());
  }

  private void process(Command command, List<Integer> input) {
    CommandChain commandChain = world.getRegistered(CommandChain.class);
    commandChain.addEnd(command.build(world, -1).setInput(input));
    world.process();

    commandChain.addEnd(
        new PrintPlayers().build(world, -1),
        new PrintForgeRow().build(world, -1),
        new PrintUnits().build(world, -1),
        new PrintPlayArea().build(world, -1),
        new PrintCurrentPlayerHand().build(world, -1));
    world.process();
  }
}

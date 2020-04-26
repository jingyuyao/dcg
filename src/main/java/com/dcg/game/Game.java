package com.dcg.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.link.EntityLinkManager;
import com.dcg.action.ActionSystem;
import com.dcg.action.ExecuteAction;
import com.dcg.api.ViewSystem;
import com.dcg.api.WorldView;
import com.dcg.command.CommandBuilder;
import com.dcg.command.CommandChain;
import com.dcg.command.CommandInvocationStrategy;
import com.dcg.effect.EffectSystem;
import com.dcg.player.PlayHandSystem;
import java.util.List;
import java.util.Random;

public class Game {
  private final WorldConfiguration configuration =
      new WorldConfigurationBuilder()
          // Uses the command pattern for execution
          .register(new CommandInvocationStrategy())
          // Order matters!
          .with(
              new EntityLinkManager(),
              new CoreSystem(),
              new PlayHandSystem(),
              new EffectSystem(),
              new ActionSystem(),
              new ViewSystem(),
              new GameOverSystem())
          .build()
          .register(new Random())
          .register(new CommandChain());
  private final World world = new World(configuration);

  public Game(List<String> players) {
    process(new SetupGame(players));
  }

  public void execute(List<Integer> input) {
    if (input.isEmpty()) {
      System.out.println("Require 1 or more inputs");
    } else {
      process(new ExecuteAction(input.get(0), input.subList(1, input.size())));
    }
  }

  public WorldView getWorldView() {
    return world.getSystem(ViewSystem.class).getWorldView();
  }

  public boolean isOver() {
    return world.getSystem(GameOverSystem.class).isOver();
  }

  private void process(CommandBuilder commandBuilder) {
    CommandChain commandChain = world.getRegistered(CommandChain.class);
    commandChain.addEnd(commandBuilder.build(world, -1));
    world.process();
  }
}

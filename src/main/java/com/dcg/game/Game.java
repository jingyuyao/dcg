package com.dcg.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.io.JsonArtemisSerializer;
import com.artemis.managers.WorldSerializationManager;
import com.dcg.action.ExecuteAction;
import com.dcg.command.CommandBuilder;
import com.dcg.command.CommandChain;
import com.dcg.command.CommandInvocationStrategy;
import com.dcg.debug.PrintVisibleWorld;
import com.dcg.effect.EffectSystem;
import com.dcg.forge.InitializeForge;
import com.dcg.player.CreatePlayer;
import com.dcg.player.PlayHandSystem;
import java.util.List;
import java.util.OptionalInt;
import java.util.Random;

public class Game {
  private final WorldSerializationManager serializationManager = new WorldSerializationManager();
  private final WorldConfiguration configuration =
      new WorldConfigurationBuilder()
          // Uses the command pattern for execution
          .register(new CommandInvocationStrategy())
          // Order matters!
          .with(new CoreSystem(), new PlayHandSystem(), new EffectSystem(), new GameOverSystem())
          .build()
          .setSystem(serializationManager)
          .register(new Random())
          .register(new CommandChain());
  private final World world = new World(configuration);

  public Game() {
    serializationManager.setSerializer(new JsonArtemisSerializer(world).prettyPrint(true));
    process(new InitializeForge());
    process(new CreatePlayer("Andrew"));
    process(new CreatePlayer("Bowen"));
    process(new CreatePlayer("Jingyu"));
    process(new InitTurn("Andrew"));
  }

  public void handleInput(List<Integer> input) {
    if (input.size() == 1) {
      process(new ExecuteAction(input.get(0), OptionalInt::empty));
    } else if (input.size() == 2) {
      process(new ExecuteAction(input.get(0), () -> OptionalInt.of(input.get(1))));
    } else {
      System.out.println("Require 1 or 2 entities as inputs");
    }
  }

  public boolean isOver() {
    return world.getSystem(GameOverSystem.class).isOver();
  }

  private void process(CommandBuilder commandBuilder) {
    CommandChain commandChain = world.getRegistered(CommandChain.class);
    commandChain.addEnd(commandBuilder.build(world, -1));
    world.process();

    commandChain.addEnd(new PrintVisibleWorld().build(world, -1));
    world.process();
  }
}

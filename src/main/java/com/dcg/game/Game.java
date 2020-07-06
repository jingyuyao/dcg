package com.dcg.game;

import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.link.EntityLinkManager;
import com.dcg.action.ActionSystem;
import com.dcg.action.ExecuteAction;
import com.dcg.api.legacy.GameView;
import com.dcg.api.legacy.ViewSystem;
import com.dcg.command.CommandBuilder;
import com.dcg.command.CommandChain;
import com.dcg.command.CommandInvocationStrategy;
import com.dcg.effect.EffectSystem;
import com.dcg.player.AddPlayCardSystem;
import com.dcg.player.Player;
import java.util.List;
import java.util.Optional;

public class Game {
  private final List<String> playerNames;
  private final WorldConfiguration configuration =
      new WorldConfigurationBuilder()
          // Uses the command pattern for execution
          .register(new CommandInvocationStrategy())
          // Order matters!
          .with(
              new EntityLinkManager(),
              new CoreSystem(),
              new AddPlayCardSystem(),
              new EffectSystem(),
              new ActionSystem(),
              new ViewSystem())
          .build()
          .register(new CommandChain());
  private final World world = new World(configuration);

  public Game(List<String> playerNames) {
    this.playerNames = playerNames;
    process(new SetupGame(playerNames));
  }

  public void execute(String playerName, List<Integer> input) {
    if (input.isEmpty()) {
      System.out.println("Require 1 or more inputs");
    } else {
      CoreSystem coreSystem = world.getSystem(CoreSystem.class);
      Optional<Integer> player =
          coreSystem.findByName(playerName, Aspect.all(Player.class)).findAny();
      if (player.isPresent()) {
        process(new ExecuteAction(input.get(0), input.subList(1, input.size())), player.get());
      } else {
        System.out.printf("Player %s not found\n", playerName);
      }
    }
  }

  public List<String> getPlayerNames() {
    return playerNames;
  }

  public GameView getGameView(String playerName) {
    return world.getSystem(ViewSystem.class).getGameView(playerName);
  }

  public void clearRecentExecutions() {
    world.getRegistered(CommandChain.class).clearExecutionBuffer();
  }

  public boolean isOver() {
    return world.getSystem(CoreSystem.class).getActivePlayerEntities().count() == 1;
  }

  private void process(CommandBuilder commandBuilder) {
    process(commandBuilder, -1);
  }

  private void process(CommandBuilder commandBuilder, int originEntity) {
    CommandChain commandChain = world.getRegistered(CommandChain.class);
    commandChain.addEnd(commandBuilder.build(world, originEntity));
    world.process();
  }
}

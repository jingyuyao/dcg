package com.dcg.game;

import com.artemis.Aspect;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.artemis.io.JsonArtemisSerializer;
import com.artemis.io.SaveFileFormat;
import com.artemis.managers.WorldSerializationManager;
import com.artemis.utils.IntBag;
import com.dcg.action.Action;
import com.dcg.action.ActionSystem;
import com.dcg.action.ExecuteAction;
import com.dcg.battle.Unit;
import com.dcg.card.Card;
import com.dcg.command.CommandBuilder;
import com.dcg.command.CommandChain;
import com.dcg.command.CommandInvocationStrategy;
import com.dcg.effect.EffectSystem;
import com.dcg.location.ForgeDeck;
import com.dcg.location.ForgeRow;
import com.dcg.location.PlayArea;
import com.dcg.location.ThroneDeck;
import com.dcg.player.PlayHandSystem;
import com.dcg.player.Player;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class Game {
  private final WorldSerializationManager serializationManager = new WorldSerializationManager();
  private final WorldConfiguration configuration =
      new WorldConfigurationBuilder()
          // Uses the command pattern for execution
          .register(new CommandInvocationStrategy())
          // Order matters!
          .with(
              new CoreSystem(),
              new PlayHandSystem(),
              new EffectSystem(),
              new ActionSystem(),
              new GameOverSystem())
          .build()
          .setSystem(serializationManager)
          .register(new Random())
          .register(new CommandChain());
  private final World world = new World(configuration);

  public Game(List<String> players) {
    serializationManager.setSerializer(new JsonArtemisSerializer(world));
    process(new SetupGame(players));
  }

  public void execute(List<Integer> input) {
    if (input.isEmpty()) {
      System.out.println("Require 1 or more inputs");
    } else {
      process(new ExecuteAction(input.get(0), input.subList(1, input.size())));
    }
  }

  public String getEntities(List<Integer> input) {
    try {
      if (input.stream().allMatch(entity -> world.getEntityManager().isActive(entity))) {
        IntBag bag = new IntBag();
        for (int entity : input) {
          bag.add(entity);
        }
        return toJson(bag);
      }
    } catch (IndexOutOfBoundsException ignored) {
    }
    return "{\"error\":\"Invalid entity\"}";
  }

  public String getDebugJson() {
    IntBag entities = world.getAspectSubscriptionManager().get(Aspect.all()).getEntities();
    return toJson(entities);
  }

  public String getWorldJson() {
    CoreSystem coreSystem = world.getSystem(CoreSystem.class);
    Stream<Integer> world =
        coreSystem.getStream(
            Aspect.all(Card.class)
                .one(ForgeDeck.class, ForgeRow.class, ThroneDeck.class, PlayArea.class));
    world =
        Stream.concat(
            world, coreSystem.getStream(Aspect.one(Player.class, Unit.class, Action.class)));
    return toJson(world.collect(CoreSystem.toIntBag()));
  }

  public boolean isOver() {
    return world.getSystem(GameOverSystem.class).isOver();
  }

  private void process(CommandBuilder commandBuilder) {
    CommandChain commandChain = world.getRegistered(CommandChain.class);
    commandChain.addEnd(commandBuilder.build(world, -1));
    world.process();
  }

  private String toJson(IntBag entities) {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    serializationManager.save(outputStream, new SaveFileFormat(entities));
    return outputStream.toString();
  }
}

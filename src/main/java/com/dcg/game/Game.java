package com.dcg.game;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.dcg.command.Command;
import com.dcg.command.CommandDeque;
import com.dcg.debug.DebugSystem;
import com.dcg.deck.Card;
import com.dcg.player.AddPlayer;
import com.dcg.player.PlayerTurnSystem;
import com.dcg.turn.AdvanceTurn;
import com.dcg.turn.InitTurn;
import java.util.ArrayList;
import java.util.List;

public class Game {

  private final WorldConfiguration configuration =
      new WorldConfigurationBuilder()
          // Allows for component inspection during removed()
          .alwaysDelayComponentRemoval(true)
          // Uses the command pattern for execution
          .register(new CommandInvocationStrategy())
          // Order matters!
          .with(new PlayerTurnSystem(), new DebugSystem())
          .build()
          .register(new CommandDeque());
  private final World world = new World(configuration);
  private boolean gameOver = false;

  public Game() {
    process(new AddPlayer("Alice", createPlayerDeck("A")));
    process(new AddPlayer("Bob", createPlayerDeck("B")));
    process(new InitTurn("Alice"));
  }

  public void handleInput(String input) {
    if (input == null) return;

    switch (input) {
      case "quit":
        gameOver = true;
        break;
      case "advance":
        process(new AdvanceTurn());
        break;
    }
  }

  public boolean isOver() {
    return gameOver;
  }

  private void process(Command command) {
    world.getRegistered(CommandDeque.class).addLast(command);
    world.process();
  }

  private static List<Card> createPlayerDeck(String prefix) {
    List<Card> cards = new ArrayList<>(10);
    for (int i = 0; i < 10; i++) {
      cards.add(new Card(prefix + i));
    }
    return cards;
  }
}

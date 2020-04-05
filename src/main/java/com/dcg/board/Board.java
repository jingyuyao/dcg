package com.dcg.board;

import com.artemis.EntityEdit;
import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.dcg.command.Command;
import com.dcg.command.CommandSystem;
import com.dcg.command.Commands;
import com.dcg.player.Player;
import com.dcg.turn.Turn;
import com.dcg.turn.TurnSystem;

public class Board {
  private final WorldConfiguration configuration =
      new WorldConfigurationBuilder()
          .with(new CommandSystem(), new TurnSystem())
          .build()
          .register(new Commands());
  private final World world = new World(configuration);

  public Board(String[] playerNames) {
    assert playerNames.length > 0;

    for (int i = 0; i < playerNames.length; i++) {
      int entity = world.create();
      EntityEdit edit = world.edit(entity);
      edit.create(Player.class).name = playerNames[i];
      if (i == 0) {
        edit.create(Turn.class);
      }
    }
  }

  public void process(Command... commands) {
    world.getRegistered(Commands.class).add(commands);
    world.process();
  }
}

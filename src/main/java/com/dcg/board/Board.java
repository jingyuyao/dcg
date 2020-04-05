package com.dcg.board;

import com.artemis.World;
import com.artemis.WorldConfiguration;
import com.artemis.WorldConfigurationBuilder;
import com.dcg.command.Command;
import com.dcg.command.CommandSystem;
import com.dcg.debug.DebugSystem;

public class Board {
  private final WorldConfiguration configuration =
      new WorldConfigurationBuilder().with(new CommandSystem(), new DebugSystem()).build();
  private final World world = new World(configuration);

  public void process(Command... commands) {
    world.getSystem(CommandSystem.class).setCurrent(commands);
    world.process();
  }
}

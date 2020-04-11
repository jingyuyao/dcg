package com.dcg.debug;

import com.artemis.World;
import com.dcg.command.Command;
import com.dcg.player.PlayerActionSystem;
import java.util.List;

public class PrintCurrentActions extends Command {
  World world;
  PlayerActionSystem playerActionSystem;

  @Override
  public void run() {
    List<Command> commands = playerActionSystem.getActions();
    for (int i = 0; i < commands.size(); i++) {
      Command command = commands.get(i);
      // Some commands require injected members for toString().
      world.inject(command);
      System.out.printf("    <%d> %s", i, command);
      System.out.println();
    }
  }
}

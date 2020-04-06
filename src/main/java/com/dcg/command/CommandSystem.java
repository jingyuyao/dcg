package com.dcg.command;

import com.artemis.BaseSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandSystem extends BaseSystem {

  private final List<Command> buffer = new ArrayList<>();

  @Override
  protected void processSystem() {
    for (Command command : buffer) {
      // Dependency injection is crazy.
      world.inject(command);
      System.out.println("running: " + command.toString());
      command.run();
    }
    buffer.clear();
  }

  public void run(Command... commands) {
    if (commands.length > 0) {
      buffer.addAll(Arrays.asList(commands));
      // We should only access entities and components while systems are being processed.
      // That's why we only queue them up in this method and leave the actual command invocation
      // to processSystem().
      getWorld().process();
    }
  }
}

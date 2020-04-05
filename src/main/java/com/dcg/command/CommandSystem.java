package com.dcg.command;

import com.artemis.BaseSystem;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandSystem extends BaseSystem {
  private List<Command> current = Collections.emptyList();

  @Override
  protected void processSystem() {
    for (Command command : current) {
      world.inject(command);
      System.out.println("running: " + command.toString());
      command.run();
    }
    current = Collections.emptyList();
  }

  public void setCurrent(Command... commands) {
    current = Arrays.asList(commands);
  }
}

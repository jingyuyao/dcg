package com.dcg.command;

import com.artemis.BaseSystem;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandSystem extends BaseSystem {
  private List<Command> current = new ArrayList<>();

  @Override
  protected void processSystem() {
    for (Command command : current) {
      world.inject(command);
      command.run();
    }
  }

  public void setCurrent(Command... commands) {
    current = Arrays.asList(commands);
  }
}

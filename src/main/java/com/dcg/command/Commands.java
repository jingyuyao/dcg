package com.dcg.command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Commands {
  private final List<Command> history = new ArrayList<>();
  private List<Command> current;

  public void add(Command... commands) {
    current = Arrays.asList(commands);
    history.addAll(current);
  }

  public List<Command> getCurrent() {
    return current;
  }

  public List<Command> getHistory() {
    return history;
  }
}

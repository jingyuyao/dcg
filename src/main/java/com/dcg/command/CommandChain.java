package com.dcg.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CommandChain {
  private final Deque<Command> queue = new LinkedList<>();
  private final List<CommandLog> history = new ArrayList<>();

  /** Add commands to the end of the deque while preserving the order of the arguments. */
  public void addEnd(Command... commands) {
    Collections.addAll(queue, commands);
  }

  public Command pop() {
    return queue.remove();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public void log(CommandLog log) {
    history.add(log);
  }

  public List<CommandLog> getHistory() {
    return history;
  }
}

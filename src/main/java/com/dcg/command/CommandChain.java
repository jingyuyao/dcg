package com.dcg.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CommandChain {
  private final List<Command> history = new ArrayList<>();
  private final Deque<Command> queue = new LinkedList<>();

  /** Add commands to the end of the deque while preserving the order of the arguments. */
  public void addEnd(List<Command> commands) {
    queue.addAll(commands);
    history.addAll(commands);
  }

  /** Add commands to the end of the deque while preserving the order of the arguments. */
  public void addEnd(Command... commands) {
    Collections.addAll(queue, commands);
    Collections.addAll(history, commands);
  }

  public Command pop() {
    return queue.remove();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }

  public List<Command> getHistory() {
    return history;
  }
}

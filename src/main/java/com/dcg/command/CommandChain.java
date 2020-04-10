package com.dcg.command;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CommandChain {

  private final Deque<Command> queue = new LinkedList<>();

  /** Add commands to the start of the deque while preserving the order of the arguments. */
  public void addStart(List<Command> commands) {
    for (int i = commands.size() - 1; i >= 0; i--) {
      queue.addFirst(commands.get(i));
    }
  }

  public void addStart(Command... commands) {
    addStart(Arrays.asList(commands));
  }

  /** Add commands to the end of the deque while preserving the order of the arguments. */
  public void addEnd(List<Command> commands) {
    for (Command command : commands) {
      queue.addLast(command);
    }
  }

  public void addEnd(Command... commands) {
    addEnd(Arrays.asList(commands));
  }

  public Command pop() {
    return queue.remove();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }
}

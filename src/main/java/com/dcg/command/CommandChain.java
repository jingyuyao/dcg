package com.dcg.command;

import java.util.Deque;
import java.util.LinkedList;

public class CommandChain {

  private final Deque<Command> queue = new LinkedList<>();

  /** Add commands to the start of the deque while preserving the order of the arguments. */
  public void addStart(Command... commands) {
    for (int i = commands.length - 1; i >= 0; i--) {
      queue.addFirst(commands[i]);
    }
  }

  /** Add commands to the end of the deque while preserving the order of the arguments. */
  public void addEnd(Command... commands) {
    for (Command command : commands) {
      queue.addLast(command);
    }
  }

  public Command pop() {
    return queue.remove();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }
}

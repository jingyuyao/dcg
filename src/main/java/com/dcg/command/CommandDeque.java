package com.dcg.command;

import java.util.Deque;
import java.util.LinkedList;

public class CommandDeque {

  private final Deque<Command> queue = new LinkedList<>();

  public void addLast(Command command) {
    queue.addLast(command);
  }

  public void addFirst(Command command) {
    queue.addFirst(command);
  }

  public Command remove() {
    return queue.remove();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }
}

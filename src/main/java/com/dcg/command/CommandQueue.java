package com.dcg.command;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class CommandQueue {

  private final Queue<Command> queue = new LinkedList<>();

  public void add(Command... commands) {
    Collections.addAll(queue, commands);
  }

  public Command remove() {
    return queue.remove();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }
}

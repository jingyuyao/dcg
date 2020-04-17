package com.dcg.command;

import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CommandChain {
  private final Deque<ExecutableCommand> queue = new LinkedList<>();

  /** Add commands to the end of the deque while preserving the order of the arguments. */
  public void addEnd(List<ExecutableCommand> executableCommands) {
    for (ExecutableCommand command : executableCommands) {
      queue.addLast(command);
    }
  }

  /** Add commands to the end of the deque while preserving the order of the arguments. */
  public void addEnd(ExecutableCommand... executableCommands) {
    addEnd(Arrays.asList(executableCommands));
  }

  public ExecutableCommand pop() {
    return queue.remove();
  }

  public boolean isEmpty() {
    return queue.isEmpty();
  }
}

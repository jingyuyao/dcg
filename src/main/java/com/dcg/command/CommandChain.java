package com.dcg.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CommandChain {
  private final List<Execution> executionBuffer = new ArrayList<>();
  private final Deque<Command> queue = new LinkedList<>();
  private final List<CommandData> log = new ArrayList<>();

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

  public void log(CommandData data) {
    log.add(data);
  }

  public List<CommandData> getLog(int startIndex) {
    return log.subList(Math.min(startIndex, log.size()), log.size());
  }

  // Stuff below is deprecated.

  public void logExecution(int executor, Command... commands) {
    for (Command command : commands) {
      executionBuffer.add(new Execution(executor, command));
    }
  }

  public List<Execution> getExecutionBuffer() {
    return this.executionBuffer;
  }

  public void clearExecutionBuffer() {
    this.executionBuffer.clear();
  }

  public static class Execution {
    private final int executor;
    private final Command command;

    public Execution(int executor, Command command) {
      this.executor = executor;
      this.command = command;
    }

    public int getExecutor() {
      return executor;
    }

    public Command getCommand() {
      return command;
    }
  }
}

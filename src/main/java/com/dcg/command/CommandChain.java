package com.dcg.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class CommandChain {
  private final List<Command> historyBuffer = new ArrayList<>();
  private final Deque<Command> queue = new LinkedList<>();

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

  public void logHistory(Command... commands) {
    Collections.addAll(historyBuffer, commands);
  }

  public List<Command> getHistoryBuffer() {
    return this.historyBuffer;
  }

  public void clearHistoryBuffer() {
    this.historyBuffer.clear();
  }
}

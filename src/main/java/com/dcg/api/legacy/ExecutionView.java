package com.dcg.api.legacy;

import com.dcg.command.Command;

public class ExecutionView {
  public final String executorName;
  public final String commandSnapshot;

  public ExecutionView(String executorName, Command command) {
    this.executorName = executorName;
    this.commandSnapshot = command.getSnapshot();
  }
}

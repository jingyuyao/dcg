package com.dcg.api;

import com.dcg.command.Command;

public class CommandView {
  public final String logText;

  public CommandView(String executorName, Command command) {
    this.logText = String.format("%s: %s", executorName, command.getSnapshot());
  }
}

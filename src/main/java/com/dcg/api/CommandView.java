package com.dcg.api;

import com.dcg.command.Command;

public class CommandView {
  public final String logText;

  public CommandView(Command command) {
    this.logText = command.toString();
  }
}

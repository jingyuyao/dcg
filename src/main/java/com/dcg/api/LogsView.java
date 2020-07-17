package com.dcg.api;

import java.util.List;

public class LogsView {
  public final int startIndex;
  public final int endIndex;
  public final List<LogView> logs;

  public LogsView(int startIndex, int endIndex, List<LogView> logs) {
    this.startIndex = startIndex;
    this.endIndex = endIndex;
    this.logs = logs;
  }
}

package com.dcg.api;

public class LogView {
  public final String currentPlayerName;
  public final String originCardName;
  public final String description;

  public LogView(String currentPlayerName, String originCardName, String description) {
    this.currentPlayerName = currentPlayerName;
    this.originCardName = originCardName;
    this.description = description;
  }
}

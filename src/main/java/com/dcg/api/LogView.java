package com.dcg.api;

public class LogView {
  public final String ownerName;
  public final String originName;
  public final String description;

  public LogView(String ownerName, String originName, String description) {
    this.ownerName = ownerName;
    this.originName = originName;
    this.description = description;
  }
}

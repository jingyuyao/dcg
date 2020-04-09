package com.dcg.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {

  private final MessageType type;
  private final String[] args;

  public Message(MessageType type, String[] args) {
    this.type = type;
    this.args = args;
  }

  public MessageType getType() {
    return type;
  }

  public List<String> getStringArgs() {
    return Arrays.asList(args);
  }

  /** Invalid integer arguments are ignored. */
  public List<Integer> getIntegerArgs() {
    List<Integer> result = new ArrayList<>(args.length);
    for (String arg : args) {
      try {
        result.add(Integer.parseInt(arg));
      } catch (NumberFormatException e) {
        System.out.println("Invalid number: " + arg);
      }
    }
    return result;
  }

  public enum MessageType {
    QUIT,
    PLAYERS,
    CHOOSE,
  }
}

package com.dcg.api;

import java.util.Collections;
import java.util.List;

public class ClientMessage {
  private Kind kind;
  private List<Integer> intArgs = Collections.emptyList();
  private List<String> strArgs = Collections.emptyList();

  public Kind getKind() {
    return kind;
  }

  public List<Integer> getIntArgs() {
    return intArgs;
  }

  public List<String> getStrArgs() {
    return strArgs;
  }

  public enum Kind {
    GET_ROOM_LIST,
    JOIN_ROOM,
    LEAVE_ROOM,
    START_GAME,
    EXECUTE_ACTION,
  }
}

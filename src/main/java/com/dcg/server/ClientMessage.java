package com.dcg.server;

import java.util.Collections;
import java.util.List;

public class ClientMessage {
  public String command = "unknown";
  public List<Integer> args = Collections.emptyList();
}

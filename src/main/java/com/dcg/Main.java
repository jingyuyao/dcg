package com.dcg;

import com.dcg.server.GameServer;
import java.net.InetSocketAddress;
import org.java_websocket.server.WebSocketServer;

public class Main {
  private static final String HOST = "0.0.0.0";
  private static final int PORT = 8888;

  public static void main(String[] args) {
    WebSocketServer server = new GameServer(new InetSocketAddress(HOST, PORT));
    server.setReuseAddr(true);
    server.run();
  }
}

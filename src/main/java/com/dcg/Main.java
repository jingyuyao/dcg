package com.dcg;

import com.dcg.server.GameServer;
import java.net.InetSocketAddress;
import org.java_websocket.server.WebSocketServer;

public class Main {
  private static final String HOST = "0.0.0.0";
  private static final int DEBUG_PORT = 8888;
  private static final int PROD_PORT = 80;

  public static void main(String[] args) {
    int port = args.length > 0 && "prod".equals(args[0]) ? PROD_PORT : DEBUG_PORT;
    WebSocketServer server = new GameServer(new InetSocketAddress(HOST, port));
    server.setReuseAddr(true);
    server.run();
  }
}

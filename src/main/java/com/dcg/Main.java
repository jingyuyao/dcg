package com.dcg;

import com.dcg.server.GameServer;
import java.net.InetSocketAddress;
import org.java_websocket.server.WebSocketServer;

public class Main {
  private static final String DEFAULT_HOST = "0.0.0.0";
  private static final int DEFAULT_DEBUG_PORT = 8888;
  private static final int DEFAULT_PROD_PORT = 80;

  public static void main(String[] args) {
    String envPort = System.getenv("PORT");
    int port;
    if (envPort != null && !envPort.trim().isEmpty()) {
      try {
        port = Integer.parseInt(envPort.trim());
      } catch (NumberFormatException e) {
        System.err.printf("Invalid PORT '%s', falling back to default\n", envPort);
        port = DEFAULT_DEBUG_PORT;
      }
    } else if (args.length > 0 && "prod".equals(args[0])) {
      port = DEFAULT_PROD_PORT;
    } else {
      port = DEFAULT_DEBUG_PORT;
    }

    String envHost = System.getenv("HOST");
    String host = (envHost != null && !envHost.trim().isEmpty()) ? envHost.trim() : DEFAULT_HOST;

    System.out.printf("Starting DCG Server on %s:%d...\n", host, port);
    WebSocketServer server = new GameServer(new InetSocketAddress(host, port));
    server.setReuseAddr(true);

    Runtime.getRuntime().addShutdownHook(new Thread(() -> {
      System.out.println("Shutting down DCG Server...");
      try {
        server.stop(1000);
        System.out.println("DCG Server stopped cleanly.");
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }));

    server.run();
  }
}


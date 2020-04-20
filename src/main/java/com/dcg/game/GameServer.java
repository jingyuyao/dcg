package com.dcg.game;

import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class GameServer extends WebSocketServer {
  private Game game = new Game();

  public GameServer(InetSocketAddress address) {
    super(address);
  }

  @Override
  public void onOpen(WebSocket conn, ClientHandshake handshake) {
    System.out.println("New connection: " + conn.getRemoteSocketAddress());
  }

  @Override
  public void onClose(WebSocket conn, int code, String reason, boolean remote) {
    System.out.println("Connection closed: " + conn.getRemoteSocketAddress());
  }

  @Override
  public void onMessage(WebSocket conn, String message) {
    System.out.print("New message: " + message);

    List<String> tokens = Arrays.asList(message.replace("\n", "").split(" "));
    if (tokens.isEmpty()) {
      System.out.println("No tokens");
      return;
    }
    switch (tokens.get(0)) {
      case "execute":
        if (tokens.size() > 1) {
          List<String> rawArguments = tokens.subList(1, tokens.size());
          Optional<List<Integer>> arguments = parse(rawArguments);
          if (arguments.isPresent()) {
            game.handleInput(arguments.get());
            if (game.isOver()) {
              System.out.println("GG");
              game = new Game();
            }
          } else {
            System.out.println("Unable to parse arguments: " + rawArguments);
          }
        } else {
          System.out.println("execute requires arguments");
        }
        break;
      case "visible":
        broadcast(game.getVisibleWorldJson());
        break;
      case "world":
        broadcast(game.getWorldJson());
        break;
      default:
        System.out.println("Unknown " + message);
        break;
    }
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    System.err.println("New connection: " + conn.getRemoteSocketAddress());
  }

  @Override
  public void onStart() {
    System.err.println("Server started");
  }

  private static Optional<List<Integer>> parse(List<String> arguments) {
    try {
      return Optional.of(arguments.stream().map(Integer::parseInt).collect(Collectors.toList()));
    } catch (RuntimeException e) {
      return Optional.empty();
    }
  }
}

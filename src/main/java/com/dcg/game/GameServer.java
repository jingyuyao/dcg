package com.dcg.game;

import com.esotericsoftware.jsonbeans.Json;
import com.esotericsoftware.jsonbeans.JsonException;
import java.net.InetSocketAddress;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class GameServer extends WebSocketServer {
  private final Json json = new Json();
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
    System.out.println("New message: " + message);

    ClientMessage clientMessage;
    try {
      clientMessage = json.fromJson(ClientMessage.class, message);
    } catch (JsonException e) {
      System.err.println("Unable to parse message: " + e);
      return;
    }

    switch (clientMessage.command) {
      case "execute":
        if (clientMessage.args.isEmpty()) {
          System.out.println("execute requires arguments");
          return;
        }
        game.execute(clientMessage.args);
        broadcast(game.getWorldJson());
        if (game.isOver()) {
          System.out.println("GG");
          game = new Game();
        }
        break;
      case "query":
        if (clientMessage.args.isEmpty()) {
          System.out.println("query requires arguments");
          return;
        }
        broadcast(game.getEntities(clientMessage.args));
        break;
      case "world":
        broadcast(game.getWorldJson());
        break;
      case "debug":
        broadcast(game.getDebugJson());
        break;
      default:
        System.out.println("Unknown " + clientMessage.command);
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
}

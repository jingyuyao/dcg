package com.dcg.server;

import com.dcg.game.Game;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.net.InetSocketAddress;
import java.util.Arrays;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class GameServer extends WebSocketServer {
  private final Gson gson = new Gson();
  private Game game;

  public GameServer(InetSocketAddress address) {
    super(address);
  }

  @Override
  public void onStart() {
    System.out.println("Server started");
    game = new Game(Arrays.asList("Edelgard", "Dimitri", "Claude"));
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
      clientMessage = gson.fromJson(message, ClientMessage.class);
    } catch (JsonSyntaxException e) {
      System.err.println("Unable to parse message: " + e);
      return;
    }

    switch (clientMessage.kind) {
      case "execute":
        if (clientMessage.args.isEmpty()) {
          System.out.println("execute requires arguments");
          return;
        }
        game.execute(clientMessage.args);
        broadcast(getWorldViewJson());
        if (game.isOver()) {
          System.out.println("GG");
          game = new Game(Arrays.asList("Edelgard", "Dimitri", "Claude"));
          broadcast(getWorldViewJson());
        }
        break;
      case "get-world-view":
        conn.send(getWorldViewJson());
        break;
      default:
        System.out.println("Unknown " + clientMessage.kind);
        break;
    }
  }

  @Override
  public void onError(WebSocket conn, Exception ex) {
    System.err.println("New connection: " + conn.getRemoteSocketAddress());
  }

  private String getWorldViewJson() {
    return gson.toJson(getWorldViewMessage());
  }

  private ServerMessage getWorldViewMessage() {
    return new ServerMessage("worldview", game.getWorldView());
  }
}

package com.dcg.server;

import com.dcg.api.ClientMessage;
import com.dcg.api.ServerMessage;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.net.InetSocketAddress;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class GameServer extends WebSocketServer {
  private final Gson gson = new Gson();
  private GameRoom gameRoom = new GameRoom();

  public GameServer(InetSocketAddress address) {
    super(address);
  }

  @Override
  public void onStart() {
    System.out.println("Server started");
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
    System.out.println("--- New Message ---");
    System.out.println("ClientMessage: " + message);

    ClientMessage clientMessage;
    try {
      clientMessage = gson.fromJson(message, ClientMessage.class);
    } catch (JsonSyntaxException e) {
      System.err.println("Unable to parse message: " + e);
      return;
    }

    switch (clientMessage.kind) {
      case "join":
        if (clientMessage.name == null) {
          System.out.println("Player name required");
        } else {
          gameRoom.join(conn, clientMessage.name);
          if (gameRoom.isInitialized()) {
            broadcast(toJson("world-view", gameRoom.getWorldView()));
          } else {
            broadcast(toJson("session-view", gameRoom.getRoomView()));
          }
        }
        break;
      case "execute":
        if (clientMessage.name == null || clientMessage.args.isEmpty()) {
          System.out.println("execute requires name and arguments");
        } else {
          gameRoom.execute(conn, clientMessage.args);
          broadcast(toJson("world-view", gameRoom.getWorldView()));
          if (gameRoom.isGameOver()) {
            System.out.println("GG");
            gameRoom = new GameRoom();
            broadcast(toJson("world-view", gameRoom.getWorldView()));
          }
        }
        break;
      case "get-room-view":
        conn.send(toJson("room-view", gameRoom.getRoomView()));
        break;
      case "get-world-view":
        conn.send(toJson("world-view", gameRoom.getWorldView()));
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

  private String toJson(String kind, Object data) {
    return gson.toJson(new ServerMessage(kind, data));
  }
}

package com.dcg.api;

import com.dcg.api.ServerMessage.Kind;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.util.Optional;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {
  private static final Logger logger = LoggerFactory.getLogger(Util.class);
  private static final Gson gson = new Gson();

  public static Optional<ClientMessage> fromJson(String message) {
    try {
      ClientMessage clientMessage = gson.fromJson(message, ClientMessage.class);
      if (clientMessage.getKind() != null) {
        return Optional.of(clientMessage);
      }
    } catch (JsonSyntaxException ignored) {
    }
    return Optional.empty();
  }

  public static void send(WebSocket socket, Kind kind, Object data) {
    String json = gson.toJson(new ServerMessage(kind, data));
    socket.send(json);
    logger.debug("Sent {} with {} char", kind, json.length());
  }
}

package com.dcg.server;

import com.dcg.api.AttachmentView;
import com.dcg.api.ClientMessage;
import com.dcg.api.ErrorView;
import com.dcg.api.GameRoomView;
import com.dcg.api.RoomList;
import com.dcg.api.ServerMessage.Kind;
import com.dcg.api.Util;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class GameServer extends WebSocketServer {
  private final Map<String, GameRoom> rooms = new ConcurrentHashMap<>();

  public GameServer(InetSocketAddress address) {
    super(address);
  }

  @Override
  public void onStart() {
    System.out.println("Server started on " + getAddress());
  }

  @Override
  public void onOpen(WebSocket socket, ClientHandshake handshake) {
    System.out.println("Server: new connection " + socket.getRemoteSocketAddress());
    socket.setAttachment(new Attachment());
    sendAttachmentView(socket);
  }

  @Override
  public void onClose(WebSocket socket, int code, String reason, boolean remote) {
    System.out.println("Server: connection closed " + socket.getRemoteSocketAddress());
    Attachment attachment = Attachment.get(socket);
    if (attachment != null) {
      attachment.getGameRoom().ifPresent(room -> room.leave(socket));
    }
  }

  @Override
  public void onError(WebSocket socket, Exception ex) {
    String source = socket != null ? String.valueOf(socket.getRemoteSocketAddress()) : "server";
    System.err.printf("Server: error from %s: %s\n", source, ex);
  }

  @Override
  public void onMessage(WebSocket socket, String message) {
    System.out.println("Server: --- New Message ---");
    System.out.println("Server: ClientMessage " + message);

    Optional<ClientMessage> clientMessage = Util.fromJson(message);
    if (!clientMessage.isPresent()) {
      System.out.println("Invalid message format: " + message);
      Util.send(socket, Kind.ERROR, new ErrorView("Invalid message format. Ensure JSON is well-formed with a valid 'kind'."));
      return;
    }
    try {
      processClientMessage(socket, clientMessage.get());
    } catch (Exception e) {
      System.err.printf("Server exception: %s\n", e);
      Util.send(socket, Kind.ERROR, new ErrorView("Internal server error: " + e.getMessage()));
    }
  }

  @Override
  public void onMessage(WebSocket socket, ByteBuffer byteBuffer) {
    String message = StandardCharsets.UTF_8.decode(byteBuffer).toString();
    onMessage(socket, message);
  }

  private void processClientMessage(WebSocket socket, ClientMessage message) {
    List<Integer> intArgs = message.getIntArgs();
    List<String> strArgs = message.getStrArgs();
    Attachment attachment = Attachment.get(socket);
    Optional<GameRoom> attachmentGameRoom = attachment != null ? attachment.getGameRoom() : Optional.empty();

    switch (message.getKind()) {
      case GET_ROOM_LIST:
        Util.send(
            socket,
            Kind.ROOM_LIST,
            new RoomList(new ArrayList<>(rooms.values()).stream().map(GameRoomView::new).collect(Collectors.toList())));
        break;

      case CREATE_ROOM:
        if (attachmentGameRoom.isPresent()) {
          Util.send(socket, Kind.ERROR, new ErrorView("Already in a room. Leave your current room before creating a new one."));
          break;
        }
        if (strArgs.isEmpty()) {
          Util.send(socket, Kind.ERROR, new ErrorView("CREATE_ROOM requires at least [roomName]."));
          break;
        }
        String roomName = strArgs.get(0);
        if (!GameRoom.isValidRoomName(roomName)) {
          Util.send(socket, Kind.ERROR, new ErrorView("Invalid room name. Must be 1-" + GameRoom.MAX_NAME_LENGTH + " characters."));
          break;
        }
        GameRoom newRoom = new GameRoom(roomName, () -> {
          rooms.remove(roomName);
          System.out.println("Server: Removed empty room " + roomName);
        });
        if (rooms.putIfAbsent(roomName, newRoom) != null) {
          newRoom.shutdown();
          Util.send(socket, Kind.ERROR, new ErrorView("Room '" + roomName + "' already exists."));
          break;
        }
        System.out.println("Server: Created new room '" + roomName + "'");
        if (strArgs.size() >= 2) {
          String playerName = strArgs.get(1);
          newRoom.join(socket, playerName);
        }
        break;

      case JOIN_ROOM:
        if (attachmentGameRoom.isPresent()) {
          Util.send(socket, Kind.ERROR, new ErrorView("Already joined a room. Leave current room first."));
          break;
        }
        if (strArgs.size() != 2) {
          Util.send(socket, Kind.ERROR, new ErrorView("JOIN_ROOM requires [roomName, playerName]."));
          break;
        }
        String targetRoomName = strArgs.get(0);
        String playerName = strArgs.get(1);
        GameRoom targetRoom = rooms.get(targetRoomName);
        if (targetRoom != null) {
          targetRoom.join(socket, playerName);
        } else {
          Util.send(socket, Kind.ERROR, new ErrorView("Room '" + targetRoomName + "' does not exist."));
        }
        break;

      case LEAVE_ROOM:
        if (attachmentGameRoom.isPresent()) {
          attachmentGameRoom.get().leave(socket);
        } else {
          Util.send(socket, Kind.ERROR, new ErrorView("Not currently in any room."));
        }
        break;

      case START_GAME:
        if (attachmentGameRoom.isPresent()) {
          attachmentGameRoom.get().start(socket);
        } else {
          Util.send(socket, Kind.ERROR, new ErrorView("Not currently in any room."));
        }
        break;

      case EXECUTE_ACTION:
        if (attachmentGameRoom.isPresent()) {
          attachmentGameRoom.get().execute(socket, intArgs);
        } else {
          Util.send(socket, Kind.ERROR, new ErrorView("Not currently in any room."));
        }
        break;

      case GET_GAME_LOGS:
        if (attachmentGameRoom.isPresent()) {
          if (intArgs.size() == 1 && intArgs.get(0) != null && intArgs.get(0) >= 0) {
            attachmentGameRoom.get().getLogs(socket, intArgs.get(0));
          } else {
            Util.send(socket, Kind.ERROR, new ErrorView("GET_GAME_LOGS requires startIndex >= 0."));
          }
        } else {
          Util.send(socket, Kind.ERROR, new ErrorView("Not currently in any room."));
        }
        break;
    }
  }

  private void sendAttachmentView(WebSocket socket) {
    Attachment attachment = Attachment.get(socket);
    if (attachment != null) {
      Util.send(socket, Kind.ATTACHMENT_VIEW, new AttachmentView(attachment));
    }
  }
}

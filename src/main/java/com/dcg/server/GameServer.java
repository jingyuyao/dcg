package com.dcg.server;

import com.dcg.api.AttachmentView;
import com.dcg.api.ClientMessage;
import com.dcg.api.GameRoomView;
import com.dcg.api.RoomList;
import com.dcg.api.ServerMessage.Kind;
import com.dcg.api.Util;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class GameServer extends WebSocketServer {
  private final List<GameRoom> rooms =
      Arrays.asList(
          new GameRoom("Room 1"),
          new GameRoom("Room 2"),
          new GameRoom("Room 3"),
          new GameRoom("Room 4"),
          new GameRoom("Room 5"));

  public GameServer(InetSocketAddress address) {
    super(address);
  }

  @Override
  public void onStart() {
    System.out.println("Server started");
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
    Attachment.get(socket).getGameRoom().ifPresent(r -> r.leave(socket));
  }

  @Override
  public void onError(WebSocket socket, Exception ex) {
    System.err.println("Server: connection error " + socket.getRemoteSocketAddress());
  }

  @Override
  public void onMessage(WebSocket socket, String message) {
    System.out.println("Server: --- New Message ---");
    System.out.println("Server: ClientMessage " + message);

    Optional<ClientMessage> clientMessage = Util.fromJson(message);
    if (!clientMessage.isPresent()) {
      System.out.println("Invalid message");
      return;
    }
    try {
      processClientMessage(socket, clientMessage.get());
    } catch (Exception e) {
      System.err.printf("Server: %s\n", e);
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
    Optional<GameRoom> attachmentGameRoom = attachment.getGameRoom();

    switch (message.getKind()) {
      case GET_ROOM_LIST:
        Util.send(
            socket,
            Kind.ROOM_LIST,
            new RoomList(rooms.stream().map(GameRoomView::new).collect(Collectors.toList())));
        break;
      case JOIN_ROOM:
        if (!attachmentGameRoom.isPresent()) {
          if (strArgs.size() == 2) {
            Optional<GameRoom> room =
                rooms.stream().filter(r -> r.getName().equals(strArgs.get(0))).findFirst();
            if (room.isPresent()) {
              room.get().join(socket, strArgs.get(1));
            } else {
              System.out.println(strArgs.get(0) + " doesn't exist");
            }
          } else {
            System.out.println("Invalid number of arguments");
          }
        } else {
          System.out.println("Already joined");
        }
        break;
      case LEAVE_ROOM:
        if (attachmentGameRoom.isPresent()) {
          attachmentGameRoom.get().leave(socket);
        } else {
          System.out.println("Not joined");
        }
        break;
      case START_GAME:
        if (attachmentGameRoom.isPresent()) {
          attachmentGameRoom.get().start(socket);
        } else {
          System.out.println("Not joined");
        }
        break;
      case EXECUTE_ACTION:
        if (attachmentGameRoom.isPresent()) {
          attachmentGameRoom.get().execute(socket, intArgs);
        } else {
          System.out.println("Not joined");
        }
        break;
      case GET_GAME_LOGS:
        if (attachmentGameRoom.isPresent()) {
          if (intArgs.size() == 1) {
            attachmentGameRoom.get().getLogs(socket, intArgs.get(0));
          } else {
            System.out.println("Invalid number of arguments");
          }
        } else {
          System.out.println("Not joined");
        }
        break;
    }
    sendAttachmentView(socket);
  }

  private void sendAttachmentView(WebSocket socket) {
    Util.send(socket, Kind.ATTACHMENT_VIEW, new AttachmentView(socket.getAttachment()));
  }
}

package com.dcg.server;

import com.dcg.api.AttachmentView;
import com.dcg.api.ClientMessage;
import com.dcg.api.RoomList;
import com.dcg.api.RoomView;
import com.dcg.api.ServerMessage.Kind;
import com.dcg.api.Util;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

public class GameServer extends WebSocketServer {
  private final GameRoom gameRoom = new GameRoom();

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

    List<Integer> intArgs = clientMessage.get().getIntArgs();
    List<String> strArgs = clientMessage.get().getStrArgs();
    Attachment attachment = Attachment.get(socket);
    Optional<GameRoom> attachmentGameRoom = attachment.getGameRoom();

    switch (clientMessage.get().getKind()) {
      case INIT_ATTACHMENT:
        if (strArgs.size() == 1) {
          attachment.setName(strArgs.get(0));
        } else {
          System.out.println("Invalid number of arguments");
        }
        break;
      case GET_ROOM_LIST:
        Util.send(
            socket,
            Kind.ROOM_LIST,
            new RoomList(Collections.singletonList(new RoomView(gameRoom))));
        break;
      case JOIN_ROOM:
        if (!attachmentGameRoom.isPresent()) {
          if (strArgs.size() == 1) {
            if (strArgs.get(0).equals("default")) {
              gameRoom.join(socket);
            } else {
              System.out.println("What room?");
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
    }
    sendAttachmentView(socket);
  }

  private void sendAttachmentView(WebSocket socket) {
    Util.send(socket, Kind.ATTACHMENT_VIEW, new AttachmentView(socket.getAttachment()));
  }
}

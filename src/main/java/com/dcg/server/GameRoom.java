package com.dcg.server;

import com.dcg.api.AttachmentView;
import com.dcg.api.ErrorView;
import com.dcg.api.GameRoomView;
import com.dcg.api.ServerMessage.Kind;
import com.dcg.api.Util;
import com.dcg.game.Game;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.java_websocket.WebSocket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An Actor / Mailbox representing a game room. All room state (players, Artemis game instance,
 * turn processing, and view broadcasting) is sequentially processed on a dedicated single-threaded executor.
 */
public class GameRoom {
  private static final Logger logger = LoggerFactory.getLogger(GameRoom.class);
  public static final int MAX_PLAYER_COUNT = 4;
  public static final int MAX_NAME_LENGTH = 32;

  private final String name;
  private final Runnable onEmptyCallback;
  private final ExecutorService executor;
  private final List<WebSocket> joined = new CopyOnWriteArrayList<>();

  private volatile boolean isGameInProgress = false;
  private volatile boolean isGameOver = false;
  private Game game;

  public GameRoom(String name) {
    this(name, null);
  }

  public GameRoom(String name, Runnable onEmptyCallback) {
    this.name = name;
    this.onEmptyCallback = onEmptyCallback;
    this.executor = Executors.newSingleThreadExecutor(r -> {
      Thread t = new Thread(r, "room-" + name);
      t.setDaemon(true);
      return t;
    });
  }

  public String getName() {
    return name;
  }

  public boolean isEmpty() {
    return joined.isEmpty();
  }

  public void join(WebSocket socket, String playerName) {
    executor.submit(() -> {
      if (!isValidPlayerName(playerName)) {
        logger.warn("[{}] Session: Invalid player name '{}'", name, playerName);
        Util.send(socket, Kind.ERROR,
            new ErrorView("Invalid player name. Must be 1-" + MAX_NAME_LENGTH + " characters."));
        return;
      }
      if (getJoinedPlayerNames().contains(playerName)) {
        logger.warn("[{}] Session: Player with name '{}' already joined", name, playerName);
        Util.send(socket, Kind.ERROR, new ErrorView("Player with name '" + playerName + "' is already in this room."));
        return;
      }
      if (joined.size() >= MAX_PLAYER_COUNT) {
        logger.warn("[{}] Session: Max player count reached", name);
        Util.send(socket, Kind.ERROR,
            new ErrorView("Room has reached maximum player capacity (" + MAX_PLAYER_COUNT + ")."));
        return;
      }
      if (isGameInProgress) {
        if (game != null && game.getPlayerNames().contains(playerName)) {
          logger.info("[{}] Session: Rejoining player '{}'", name, playerName);
          joinInternal(socket, playerName);
          broadcastGameView();
        } else {
          logger.warn("[{}] Session: Game has already begun, reject join for '{}'", name, playerName);
          Util.send(socket, Kind.ERROR, new ErrorView("Game has already begun in this room."));
        }
      } else {
        logger.info("[{}] Session: Joining player '{}'", name, playerName);
        joinInternal(socket, playerName);
      }
    });
  }

  private void joinInternal(WebSocket socket, String playerName) {
    Attachment attachment = Attachment.get(socket);
    if (attachment != null) {
      attachment.setPlayerName(playerName);
      attachment.setGameRoom(this);
      Util.send(socket, Kind.ATTACHMENT_VIEW, new AttachmentView(attachment));
    }
    joined.add(socket);
    broadcastRoomView();
  }

  public void leave(WebSocket socket) {
    executor.submit(() -> {
      joined.remove(socket);
      Attachment attachment = Attachment.get(socket);
      String pName = (attachment != null) ? attachment.getPlayerName().orElse("unknown") : "unknown";
      logger.info("[{}] Session: Player '{}' left", name, pName);
      if (attachment != null) {
        attachment.setPlayerName(null);
        attachment.setGameRoom(null);
        Util.send(socket, Kind.ATTACHMENT_VIEW, new AttachmentView(attachment));
      }
      if (joined.isEmpty()) {
        game = null;
        isGameInProgress = false;
        isGameOver = false;
        if (onEmptyCallback != null) {
          onEmptyCallback.run();
        }
        executor.shutdown();
      } else {
        broadcastRoomView();
      }
    });
  }

  public void start(WebSocket socket) {
    executor.submit(() -> {
      if (isGameInProgress && !isGameOver) {
        logger.warn("[{}] Session: Game has already begun", name);
        Util.send(socket, Kind.ERROR, new ErrorView("Game is already in progress."));
      } else if (joined.size() <= 1) {
        logger.warn("[{}] Session: Not enough players to start (current: {})", name, joined.size());
        Util.send(socket, Kind.ERROR, new ErrorView("At least 2 players are required to start a game."));
      } else {
        logger.info("[{}] Session: Starting game with players: {}", name, getJoinedPlayerNames());
        game = new Game(getJoinedPlayerNames());
        isGameInProgress = true;
        isGameOver = false;
        broadcastRoomView();
        broadcastGameView();
      }
    });
  }

  public void execute(WebSocket socket, List<Integer> args) {
    executor.submit(() -> {
      if (game == null) {
        logger.warn("[{}] Session: Game is not started yet.", name);
        Util.send(socket, Kind.ERROR, new ErrorView("Game is not started yet."));
        return;
      }
      if (isGameOver) {
        logger.warn("[{}] Session: Game is over.", name);
        Util.send(socket, Kind.ERROR, new ErrorView("Game is already over."));
        return;
      }

      Attachment attachment = Attachment.get(socket);
      Optional<String> nameOpt = attachment != null ? attachment.getPlayerName() : Optional.empty();
      if (!nameOpt.isPresent()) {
        logger.warn("[{}] Session: Player needs a name", name);
        Util.send(socket, Kind.ERROR, new ErrorView("Player does not have a registered name in this room."));
        return;
      }

      game.execute(nameOpt.get(), args);
      if (game.isOver()) {
        isGameOver = true;
        logger.info("[{}] Session: Game has ended", name);
        broadcastRoomView();
      }
      broadcastGameView();
    });
  }

  public void getLogs(WebSocket socket, int startIndex) {
    executor.submit(() -> {
      if (game == null) {
        logger.warn("[{}] Session: Game is not started yet.", name);
        Util.send(socket, Kind.ERROR, new ErrorView("Game is not started yet."));
        return;
      }

      Util.send(socket, Kind.GAME_LOGS, game.getLogsView(startIndex));
    });
  }

  public List<String> getJoinedPlayerNames() {
    return joined.stream()
        .map(c -> {
          Attachment a = Attachment.get(c);
          return a != null ? a.getPlayerName().orElse("") : "";
        })
        .collect(Collectors.toList());
  }

  public boolean isGameInProgress() {
    return isGameInProgress;
  }

  public boolean isGameOver() {
    return isGameOver;
  }

  public void shutdown() {
    executor.shutdownNow();
  }

  private void broadcastRoomView() {
    for (WebSocket socket : joined) {
      Util.send(socket, Kind.GAME_ROOM_VIEW, new GameRoomView(this));
    }
  }

  private void broadcastGameView() {
    for (WebSocket socket : joined) {
      Attachment a = Attachment.get(socket);
      String playerName = a != null ? a.getPlayerName().orElse("") : "";
      if (game != null) {
        Util.send(socket, Kind.GAME_VIEW, game.getGameView(playerName));
      }
    }
  }

  public static boolean isValidPlayerName(String playerName) {
    return playerName != null
        && !playerName.trim().isEmpty()
        && playerName.trim().equals(playerName)
        && playerName.length() <= MAX_NAME_LENGTH;
  }

  public static boolean isValidRoomName(String roomName) {
    return roomName != null
        && !roomName.trim().isEmpty()
        && roomName.length() <= MAX_NAME_LENGTH;
  }
}

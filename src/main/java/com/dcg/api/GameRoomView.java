package com.dcg.api;

import com.dcg.server.GameRoom;
import java.util.List;

public class GameRoomView {
  public final String roomName;
  public final List<String> joinPlayerNames;
  public final boolean isGameInProgress;

  public GameRoomView(GameRoom gameRoom) {
    this.roomName = gameRoom.getRoomName();
    this.joinPlayerNames = gameRoom.getJoinedPlayerNames();
    this.isGameInProgress = gameRoom.isGameInProgress();
  }
}

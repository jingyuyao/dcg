package com.dcg.api;

import com.dcg.server.GameRoom;
import java.util.List;

public class RoomView {
  public final String roomName;
  public final List<String> playerNames;
  public final boolean isGameInProgress;

  public RoomView(GameRoom gameRoom) {
    this.roomName = gameRoom.getRoomName();
    this.playerNames = gameRoom.getPlayerNames();
    this.isGameInProgress = gameRoom.isGameInProgress();
  }
}

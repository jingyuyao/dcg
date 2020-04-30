package com.dcg.api;

import java.util.List;

public class RoomList {
  public final List<GameRoomView> rooms;

  public RoomList(List<GameRoomView> rooms) {
    this.rooms = rooms;
  }
}

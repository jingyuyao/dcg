package com.dcg.api;

import com.dcg.server.Attachment;
import com.dcg.server.GameRoom;

public class AttachmentView {
  public final String playerName;
  public final String roomName;

  public AttachmentView(Attachment attachment) {
    this.playerName = attachment.getPlayerName().orElse(null);
    this.roomName = attachment.getGameRoom().map(GameRoom::getName).orElse(null);
  }
}

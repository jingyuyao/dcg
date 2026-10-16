package com.dcg.api;

import com.dcg.server.Attachment;
import com.dcg.server.GameRoom;

public class AttachmentView {
  public final String playerName;
  /**
   * Only sent to the socket it belongs to. Send it back as the third JOIN_ROOM argument to rejoin a
   * game in progress.
   */
  public final String rejoinToken;

  public final String roomName;

  public AttachmentView(Attachment attachment) {
    this.playerName = attachment.getPlayerName().orElse(null);
    this.rejoinToken = attachment.getRejoinToken().orElse(null);
    this.roomName = attachment.getGameRoom().map(GameRoom::getName).orElse(null);
  }
}

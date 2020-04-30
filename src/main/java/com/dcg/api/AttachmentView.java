package com.dcg.api;

import com.dcg.server.Attachment;
import com.dcg.server.GameRoom;

public class AttachmentView {
  public final String name;
  public final String roomName;

  public AttachmentView(Attachment attachment) {
    this.name = attachment.getName().orElse(null);
    this.roomName = attachment.getGameRoom().map(GameRoom::getRoomName).orElse(null);
  }
}

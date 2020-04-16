package com.dcg.player;

import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;

public class DrawCards extends Command {
  private final int playerEntity;
  private final int num;
  @Wire CommandChain commandChain;

  public DrawCards(int num) {
    this.playerEntity = -1;
    this.num = num;
  }

  public DrawCards(int playerEntity, int num) {
    this.playerEntity = playerEntity;
    this.num = num;
  }

  @Override
  protected void run() {
    for (int i = 0; i < num; i++) {
      commandChain.addStart(playerEntity != -1 ? new DrawCard(playerEntity) : new DrawCard());
    }
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), num);
  }
}

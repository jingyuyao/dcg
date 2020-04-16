package com.dcg.player;

import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;

public class DrawCards extends Command {
  private final int num;
  @Wire protected CommandChain commandChain;

  public DrawCards(int num) {
    this.num = num;
  }

  @Override
  protected void run() {
    for (int i = 0; i < num; i++) {
      commandChain.addEnd(new DrawCard().setOwner(owner));
    }
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), num);
  }
}

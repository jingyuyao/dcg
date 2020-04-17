package com.dcg.ownership;

import com.artemis.ComponentMapper;
import com.dcg.command.CommandBase;

public class Own extends CommandBase {
  private final int ownedEntity;
  protected ComponentMapper<Owned> mOwned;

  public Own(int ownedEntity) {
    this.ownedEntity = ownedEntity;
  }

  @Override
  protected void run() {
    mOwned.create(ownedEntity).owner = owner;
  }

  @Override
  public String toString() {
    return String.format("%s owned *%d", super.toString(), ownedEntity);
  }
}

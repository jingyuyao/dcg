package com.dcg.ownership;

import com.artemis.ComponentMapper;
import com.dcg.command.Command;

public class Own extends Command {
  private final int ownerEntity;
  private final int ownedEntity;
  protected ComponentMapper<Owned> mOwned;

  public Own(int ownerEntity, int ownedEntity) {
    this.ownerEntity = ownerEntity;
    this.ownedEntity = ownedEntity;
  }

  @Override
  public void run() {
    mOwned.create(ownedEntity).owner = ownerEntity;
  }

  @Override
  public String toString() {
    return super.toString() + ownedEntity + " by " + ownerEntity;
  }
}

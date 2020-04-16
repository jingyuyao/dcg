package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.Command;

public class AddDefense extends Command {
  private final int defense;
  private boolean addToOwner = false;
  protected ComponentMapper<Unit> mUnit;

  public AddDefense(int defense) {
    this.defense = defense;
  }

  public AddDefense toOwner() {
    this.addToOwner = true;
    return this;
  }

  @Override
  protected boolean isInputValid() {
    return addToOwner || (input.size() == 1 && mUnit.has(input.get(0)));
  }

  @Override
  public void run() {
    int targetEntity = addToOwner ? owner : input.get(0);
    mUnit.get(targetEntity).defense += defense;
  }

  @Override
  public String toString() {
    return String.format("%s %d", super.toString(), defense);
  }
}

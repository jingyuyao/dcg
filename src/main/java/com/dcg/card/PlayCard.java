package com.dcg.card;

import com.artemis.annotations.Wire;
import com.dcg.command.CommandBase;
import com.dcg.command.CommandChain;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;

public class PlayCard extends CommandBase {
  @Wire protected CommandChain commandChain;

  @Override
  protected void run() {
    commandChain.addEnd(new MoveLocation(PlayArea.class).build(world, sourceEntity));
  }
}

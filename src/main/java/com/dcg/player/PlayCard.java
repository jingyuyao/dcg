package com.dcg.player;

import com.artemis.annotations.Wire;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandChain;
import com.dcg.command.CommandData;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayArea;

public class PlayCard extends AbstractCommandBuilder {
  @Wire protected CommandChain commandChain;

  @Override
  protected void run(CommandData data) {
    commandChain.addEnd(new MoveLocation(PlayArea.class).build(world, data.getOriginEntity()));
  }

  @Override
  protected String getDescription(CommandData data) {
    return String.format("%s", coreSystem.toName(data.getOriginEntity()));
  }
}

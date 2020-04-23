package com.dcg.card;

import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.InputSelector;
import com.dcg.target.Target;
import com.dcg.target.VoidbindableCards;

public class DeleteCard extends AbstractCommandBuilder {
  public DeleteCard() {
    setTargetSource(new VoidbindableCards());
    setTargetCount(1, 1);
    setTargetSelector(new InputSelector());
  }

  @Override
  protected void run(Target target) {
    target.getTargets().forEach(world::delete);
  }
}

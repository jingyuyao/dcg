package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.target.Inputs;
import com.dcg.target.Target;

public class DeleteCard extends AbstractCommandBuilder {
  protected ComponentMapper<Card> mCard;

  public DeleteCard() {
    setTargetFunction(new Inputs());
    addTargetConditions(target -> target.getTargets().stream().allMatch(mCard::has));
  }

  @Override
  protected void run(Target target) {
    target.getTargets().forEach(world::delete);
  }
}

package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;
import com.dcg.source.Inputs;

public class DeleteCard extends AbstractCommandBuilder {
  protected ComponentMapper<Card> mCard;

  public DeleteCard() {
    setTargetFunction(new Inputs());
    addTargetConditions(target -> target.getTo().stream().allMatch(mCard::has));
  }

  @Override
  protected void run(Target target) {
    target.getTo().forEach(world::delete);
  }
}

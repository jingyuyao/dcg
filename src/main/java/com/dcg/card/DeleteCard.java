package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Target;
import com.dcg.targetsource.Inputs;

public class DeleteCard extends AbstractCommandBuilder {
  protected ComponentMapper<Card> mCard;

  public DeleteCard() {
    setTargetSource(new Inputs());
    addTargetConditions(target -> target.get().stream().allMatch(mCard::has));
  }

  @Override
  protected void run(Target target) {
    target.get().forEach(world::delete);
  }
}

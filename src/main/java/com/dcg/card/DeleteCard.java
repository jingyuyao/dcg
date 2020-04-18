package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.targetsource.Inputs;
import java.util.List;

public class DeleteCard extends AbstractCommandBuilder {
  protected ComponentMapper<Card> mCard;

  public DeleteCard() {
    setTargetSource(new Inputs());
    addTargetConditions(input -> input.stream().allMatch(mCard::has));
  }

  @Override
  protected void run(List<Integer> input) {
    getTargetEntities(input).forEach(world::delete);
  }
}

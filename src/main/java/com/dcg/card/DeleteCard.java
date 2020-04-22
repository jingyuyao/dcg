package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.target.PlayAreaOrDiscardPileInputs;
import com.dcg.target.Target;
import net.mostlyoriginal.api.utils.Preconditions;

public class DeleteCard extends AbstractCommandBuilder {
  protected ComponentMapper<Card> mCard;

  public DeleteCard() {
    setTargetSource(new PlayAreaOrDiscardPileInputs());
    addTargetConditions(
        target ->
            Preconditions.checkArgument(
                target.getTargets().stream().allMatch(mCard::has), "Target must be a card"));
  }

  @Override
  protected void run(Target target) {
    target.getTargets().forEach(world::delete);
  }
}

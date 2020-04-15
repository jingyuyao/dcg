package com.dcg.card;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.PlayArea;

@All({Card.class, PlayArea.class})
public class CardEffectSystem extends BaseEntitySystem {
  @Wire CommandChain commandChain;
  protected World world;
  protected ComponentMapper<Card> mCard;

  @Override
  protected void inserted(int cardEntity) {
    Card card = mCard.get(cardEntity);
    // Looping in reverse so the effects are added to the chain in the correct order.
    for (int i = card.effects.size() - 1; i >= 0; i--) {
      Command command = card.effects.get(i);
      world.inject(command);
      if (command.canRun()) {
        commandChain.addStart(command);
      } else {
        commandChain.addStart(new CreateAction(cardEntity, command));
      }
    }
  }

  @Override
  protected void processSystem() {}
}

package com.dcg.player;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.CommandChain;
import com.dcg.location.PlayArea;

@All({Card.class, PlayArea.class})
public class AddPowerSystem extends BaseEntitySystem {
  @Wire protected CommandChain commandChain;
  protected ComponentMapper<Card> mCard;

  @Override
  protected void inserted(int entityId) {
    super.inserted(entityId);
    Card card = mCard.get(entityId);
    if (card.power > 0) {
      commandChain.addStart(new AdjustPower(card.power));
    }
  }

  @Override
  protected void processSystem() {}
}

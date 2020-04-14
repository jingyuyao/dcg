package com.dcg.effect;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.location.PlayArea;
import com.dcg.turn.TurnSystem;

@All({Card.class, PlayArea.class, OnPlay.class})
public class OnPlaySystem extends BaseEntitySystem {
  @Wire CommandChain commandChain;
  protected TurnSystem turnSystem;
  protected ComponentMapper<OnPlay> mOnPlay;

  @Override
  protected void inserted(int cardEntity) {
    OnPlay onPlay = mOnPlay.get(cardEntity);
    for (Command effect : onPlay.effects) {
      if (effect.isInputRequired()) {
        turnSystem.getCurrentTurn().commands.add(effect);
      } else {
        commandChain.addEnd(effect);
      }
    }
  }

  @Override
  protected void processSystem() {}
}

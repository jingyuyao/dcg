package com.dcg.card;

import com.artemis.BaseEntitySystem;
import com.artemis.ComponentMapper;
import com.artemis.annotations.All;
import com.artemis.annotations.Wire;
import com.dcg.action.CreateAction;
import com.dcg.command.CommandChain;
import com.dcg.location.PlayArea;
import java.util.stream.Collectors;

@All({Card.class, PlayArea.class, OnPlay.class})
public class OnPlaySystem extends BaseEntitySystem {
  @Wire CommandChain commandChain;
  protected ComponentMapper<OnPlay> mOnPlay;

  @Override
  protected void inserted(int cardEntity) {
    OnPlay onPlay = mOnPlay.get(cardEntity);
    commandChain.addStart(
        onPlay.actions.stream()
            .map(command -> new CreateAction(cardEntity, command))
            .collect(Collectors.toList()));
    commandChain.addStart(onPlay.effects);
  }

  @Override
  protected void processSystem() {}
}

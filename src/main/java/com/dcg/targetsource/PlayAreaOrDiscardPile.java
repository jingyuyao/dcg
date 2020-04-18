package com.dcg.targetsource;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.command.Input;
import com.dcg.command.Target;
import com.dcg.game.CoreSystem;
import com.dcg.location.DiscardPile;
import com.dcg.location.PlayArea;
import java.util.stream.Collectors;

public class PlayAreaOrDiscardPile implements TargetSource {
  protected CoreSystem coreSystem;

  @Override
  public Target apply(Integer sourceEntity, Input input) {
    return () ->
        coreSystem
            .getCurrentPlayerEntity()
            .flatMap(
                playerEntity ->
                    coreSystem.getStream(
                        Aspect.all(Card.class).one(PlayArea.class, DiscardPile.class)))
            .boxed()
            .collect(Collectors.toList());
  }
}

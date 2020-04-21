package com.dcg.condition;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.game.CoreSystem;
import com.dcg.location.DiscardPile;
import com.dcg.location.PlayArea;
import com.dcg.target.Target;
import java.util.List;
import java.util.stream.Collectors;
import net.mostlyoriginal.api.utils.Preconditions;

public class PlayAreaOrDiscardPile implements TargetCondition {
  protected CoreSystem coreSystem;

  @Override
  public void accept(Target target) {
    List<Integer> playAreaOrDiscardPile =
        coreSystem
            .getStream(Aspect.all(Card.class).one(PlayArea.class, DiscardPile.class))
            .boxed()
            .collect(Collectors.toList());
    Preconditions.checkArgument(
        playAreaOrDiscardPile.containsAll(target.getTargets()),
        "Target is not in play area or discard pile");
  }
}

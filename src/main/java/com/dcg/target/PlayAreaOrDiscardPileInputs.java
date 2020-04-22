package com.dcg.target;

import com.artemis.Aspect;
import com.dcg.card.Card;
import com.dcg.location.DiscardPile;
import com.dcg.location.PlayArea;
import java.util.List;
import java.util.stream.Collectors;

public class PlayAreaOrDiscardPileInputs extends Inputs {
  public PlayAreaOrDiscardPileInputs() {}

  public PlayAreaOrDiscardPileInputs(int maxInputs) {
    super(maxInputs);
  }

  public PlayAreaOrDiscardPileInputs(int minInputs, int maxInputs) {
    super(minInputs, maxInputs);
  }

  @Override
  public List<Integer> getAllowedInputs() {
    return coreSystem
        .getStream(Aspect.all(Card.class).one(PlayArea.class, DiscardPile.class))

        .collect(Collectors.toList());
  }
}

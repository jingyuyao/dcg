package com.dcg.debug;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.location.ForgeRow;
import com.dcg.util.AspectSystem;
import java.util.List;

public class PrintForgeRow extends Command {
  protected AspectSystem aspectSystem;
  protected ComponentMapper<Card> mCard;

  @Override
  public void run() {
    List<Integer> forgeRow = aspectSystem.get(Aspect.all(Card.class, ForgeRow.class));
    for (int cardEntity : forgeRow) {
      System.out.printf("    *%d %s\n", cardEntity, mCard.get(cardEntity));
    }
  }
}

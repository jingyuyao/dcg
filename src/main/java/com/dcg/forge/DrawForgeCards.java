package com.dcg.forge;

import static com.dcg.action.CreateAction.action;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandData;
import com.dcg.game.Util;
import com.dcg.location.ForgeDeck;
import com.dcg.location.ForgeRow;
import com.dcg.location.MoveLocation;
import java.util.List;
import java.util.stream.Collectors;

public class DrawForgeCards extends AbstractCommandBuilder {
  protected ComponentMapper<Card> mCard;

  public DrawForgeCards(int num) {
    setIntArgSupplier(() -> num);
  }

  @Override
  protected void run(CommandData data) {
    List<Integer> forgeDeck =
        coreSystem.getStream(Aspect.all(Card.class, ForgeDeck.class)).collect(Collectors.toList());
    List<Integer> drawnCardEntities = Util.pickRandom(forgeDeck, data.getInt());
    for (int cardEntity : drawnCardEntities) {
      commandChain.addEnd(
          new MoveLocation(ForgeRow.class).build(world, cardEntity),
          action("Buy", new BuyCard().chain(new DrawForgeCards(1))).build(world, cardEntity));
      Card card = mCard.get(cardEntity);
      if (card.canWarp) {
        commandChain.addEnd(
            action(new Warp().chain(new DrawForgeCards(1))).build(world, cardEntity));
      }
    }
    if (drawnCardEntities.size() < data.getInt()) {
      System.out.printf(
          "%s cards not drawn from forge\n", data.getInt() - drawnCardEntities.size());
    }
  }
}

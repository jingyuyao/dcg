package com.dcg.forge;

import static com.dcg.action.CreateAction.action;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.location.ForgeDeck;
import com.dcg.location.ForgeRow;
import com.dcg.location.MoveLocation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class DrawForgeCards extends AbstractCommandBuilder {
  @Wire protected Random random;
  protected ComponentMapper<Card> mCard;

  public DrawForgeCards(int num) {
    setIntArgSupplier(() -> num);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    List<Integer> forgeDeck =
        coreSystem.getStream(Aspect.all(Card.class, ForgeDeck.class)).collect(Collectors.toList());
    for (int i = 0; i < args.getInt(); i++) {
      if (!forgeDeck.isEmpty()) {
        int cardIndex = random.nextInt(forgeDeck.size());
        int cardEntity = forgeDeck.get(cardIndex);
        forgeDeck.remove(cardIndex);
        commandChain.addEnd(
            new MoveLocation(ForgeRow.class).build(world, cardEntity),
            action(new BuyCard().chain(new DrawForgeCards(1))).build(world, cardEntity));
        Card card = mCard.get(cardEntity);
        if (card.canWarp) {
          commandChain.addEnd(
              action(new Warp().chain(new DrawForgeCards(1))).build(world, cardEntity));
        }
      } else {
        System.out.println("No more forge cards");
        break;
      }
    }
  }
}

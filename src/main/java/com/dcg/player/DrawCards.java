package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.location.Deck;
import com.dcg.location.DiscardPile;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class DrawCards extends PlayerEffect {
  @Wire protected Random random;

  public DrawCards(int num) {
    setCommandValue(() -> num);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    for (int playerEntity : targets) {
      List<Integer> deck = getDeck(playerEntity).collect(Collectors.toList());
      List<Integer> discardPile = getDiscardPile(playerEntity).collect(Collectors.toList());

      boolean reshuffleDiscard = false;
      for (int i = 0; i < value; i++) {
        if (!deck.isEmpty()) {
          drawFrom(deck);
        } else if (!discardPile.isEmpty()) {
          drawFrom(discardPile);
          reshuffleDiscard = true;
        } else {
          System.out.printf("No cards in deck or discard pile, %d cards not drawn\n", value - i);
          break;
        }
      }

      if (reshuffleDiscard) {
        commandChain.addEnd(new ReshuffleDiscardPile().build(world, playerEntity));
      }
    }
  }

  private Stream<Integer> getDeck(int playerEntity) {
    return coreSystem.getChildren(playerEntity, Aspect.all(Card.class, Deck.class));
  }

  private Stream<Integer> getDiscardPile(int playerEntity) {
    return coreSystem.getChildren(playerEntity, Aspect.all(Card.class, DiscardPile.class));
  }

  private void drawFrom(List<Integer> cards) {
    int cardIndex = random.nextInt(cards.size());
    int cardEntity = cards.get(cardIndex);
    cards.remove(cardIndex);
    commandChain.addEnd(new MoveLocation(Hand.class).build(world, cardEntity));
  }
}

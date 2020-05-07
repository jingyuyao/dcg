package com.dcg.player;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.CommandArgs;
import com.dcg.game.Util;
import com.dcg.location.DiscardPile;
import com.dcg.location.Hand;
import com.dcg.location.MoveLocation;
import com.dcg.location.PlayerDeck;
import java.util.List;
import java.util.stream.Collectors;

public class DrawCards extends PlayerEffect {
  protected ComponentMapper<Player> mPlayer;

  private DrawCards(int num) {
    setIntArgSupplier(() -> num);
  }

  public static DrawCards draw(int num) {
    return new DrawCards(num);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    for (int playerEntity : targets) {
      drawCards(playerEntity, args.getInt());
    }
  }

  private void drawCards(int playerEntity, int totalToDraw) {
    Player player = mPlayer.get(playerEntity);
    if (!player.drawCardLock) {
      List<Integer> drawnCardEntities = drawFromDeck(playerEntity, totalToDraw);

      // Draw cards from deck if possible.
      if (!drawnCardEntities.isEmpty()) {
        player.drawCardLock = true;
        for (int cardEntity : drawnCardEntities) {
          commandChain.addEnd(new MoveLocation(Hand.class).build(world, cardEntity));
        }
      }

      int leftOverDrawCount = totalToDraw - drawnCardEntities.size();
      // If we didn't draw enough from the deck.
      if (leftOverDrawCount > 0) {
        List<Integer> discardPile = getDiscardPile(playerEntity);
        // Shuffling discard pile back into deck and then queue up drawing the remaining cards.
        if (!discardPile.isEmpty()) {
          player.drawCardLock = true;
          for (int cardEntity : discardPile) {
            commandChain.addEnd(new MoveLocation(PlayerDeck.class).build(world, cardEntity));
          }
          commandChain.addEnd(draw(leftOverDrawCount).build(world, playerEntity));
        } else {
          System.out.printf("No more cards to draw: %d not drawn\n", leftOverDrawCount);
        }
      }

      // Unlock the card draw at the end of our processing commands if we locked it.
      if (player.drawCardLock) {
        commandChain.addEnd(new UnlockDrawCard().build(world, playerEntity));
      }
    } else {
      // Delay the card draw to the end of the chain if we are currently in the processing of
      // drawing or shuffling.
      commandChain.addEnd(draw(totalToDraw).build(world, playerEntity));
    }
  }

  private List<Integer> drawFromDeck(int playerEntity, int totalToDraw) {
    List<Integer> deck =
        coreSystem
            .getChildren(playerEntity, Aspect.all(Card.class, PlayerDeck.class))
            .collect(Collectors.toList());
    return Util.pickRandom(deck, totalToDraw);
  }

  private List<Integer> getDiscardPile(int playerEntity) {
    return coreSystem
        .getChildren(playerEntity, Aspect.all(Card.class, DiscardPile.class))
        .collect(Collectors.toList());
  }

  private static class UnlockDrawCard extends AbstractCommandBuilder {
    protected ComponentMapper<Player> mPlayer;

    @Override
    protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
      Player player = mPlayer.get(originEntity);
      player.drawCardLock = false;
    }
  }
}

package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.effect.CreateUnit;
import com.dcg.effect.GeneratePower;
import com.dcg.effect.OnPlay;
import com.dcg.location.Deck;
import com.dcg.ownership.Owned;
import java.util.Random;

public class CreatePlayer extends Command {
  private final String name;
  @Wire CommandChain commandChain;
  @Wire protected Random random;
  protected World world;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Deck> mDeck;
  protected ComponentMapper<OnPlay> mOnPlay;

  public CreatePlayer(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    int playerEntity = world.create();
    mPlayer.create(playerEntity).name = name;
    for (int i = 0; i < 7; i++) {
      int cardEntity = world.create();
      Card card = mCard.create(cardEntity);
      card.name = "P" + i;
      OnPlay onPlay = mOnPlay.create(cardEntity);
      if (random.nextBoolean()) {
        onPlay.effects.add(new CreateUnit(card.name, random.nextInt(5) + 1));
      } else {
        onPlay.effects.add(new GeneratePower(random.nextInt(2) + 1));
      }
      mDeck.create(cardEntity);
      mOwned.create(cardEntity).owner = playerEntity;
    }
    commandChain.addStart(new DrawCards(playerEntity, 5));
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), name);
  }
}

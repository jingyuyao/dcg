package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.card.Power;
import com.dcg.card.Strength;
import com.dcg.command.Command;
import com.dcg.location.Deck;
import com.dcg.ownership.Owned;
import java.util.Random;

public class CreatePlayer extends Command {
  private final String name;
  @Wire protected Random random;
  protected World world;
  protected ComponentMapper<Player> mPlayer;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Deck> mDeck;
  protected ComponentMapper<Strength> mStrength;
  protected ComponentMapper<Power> mPower;

  public CreatePlayer(String name) {
    this.name = name;
  }

  @Override
  public void run() {
    int playerEntity = world.create();
    mPlayer.create(playerEntity).name = name;
    for (int i = 0; i < 7; i++) {
      int cardEntity = world.create();
      mCard.create(cardEntity).name = "P" + i;
      mDeck.create(cardEntity);
      mOwned.create(cardEntity).owner = playerEntity;
      if (random.nextBoolean()) {
        mStrength.create(cardEntity).value = random.nextInt(5) + 1;
      } else {
        mPower.create(cardEntity);
      }
    }
  }

  @Override
  public String toString() {
    return super.toString() + name;
  }
}

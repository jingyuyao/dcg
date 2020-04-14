package com.dcg.player;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.card.Card;
import com.dcg.command.Command;
import com.dcg.effect.CreateUnit;
import com.dcg.effect.GeneratePower;
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
  protected ComponentMapper<CreateUnit> mCreateUnit;
  protected ComponentMapper<GeneratePower> mAddPower;

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
      card.name = "P";
      if (random.nextBoolean()) {
        CreateUnit createUnit = mCreateUnit.create(cardEntity);
        createUnit.name = card.name;
        createUnit.strength = random.nextInt(5) + 1;
      } else {
        mAddPower.create(cardEntity);
      }
      mDeck.create(cardEntity);
      mOwned.create(cardEntity).owner = playerEntity;
    }
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), name);
  }
}

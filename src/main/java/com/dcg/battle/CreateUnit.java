package com.dcg.battle;

import static com.dcg.action.CreateAction.action;

import com.artemis.ComponentMapper;
import com.dcg.card.Card;
import com.dcg.command.CommandData;
import com.dcg.game.CreateEntity;
import com.dcg.game.Owned;
import com.dcg.game.Preconditions;

public class CreateUnit extends CreateEntity {
  public final int strength;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Unit> mUnit;
  protected ComponentMapper<Defending> mDefending;

  private CreateUnit(String name, int strength, boolean hasBlock) {
    super(name);
    this.strength = strength;
    if (hasBlock) {
      addOnEnterEffects(action(new Block()));
    }
  }

  public static CreateUnit unitToken(String name, int strength) {
    return new CreateUnit(name, strength, true);
  }

  public static CreateUnit unitTokenNoBlock(String name, int strength) {
    return new CreateUnit(name, strength, false);
  }

  @Override
  protected void run(CommandData data) {
    int originEntity = data.getOriginEntity();
    Preconditions.checkGameState(originEntity != -1, "Must have card entity for CreateUnit");
    int ownerEntity = coreSystem.getRoot(originEntity);
    Preconditions.checkGameState(ownerEntity != -1, "Must have owner for CreateUnit");
    int unitEntity = createEntity();
    mOwned.create(unitEntity).owner = ownerEntity;
    Unit unit = mUnit.create(unitEntity);
    unit.cardEntity = getCardEntity(originEntity);
    unit.strength = strength;
    mDefending.create(unitEntity);
  }

  private int getCardEntity(int originEntity) {
    Preconditions.checkGameState(
        mCard.has(originEntity) || mUnit.has(originEntity),
        "CreateUnit must be spawned from either a card or an unit");

    if (mCard.has(originEntity)) {
      return originEntity;
    } else {
      return mUnit.get(originEntity).cardEntity;
    }
  }
}

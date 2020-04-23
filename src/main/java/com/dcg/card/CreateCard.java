package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.battle.CreateUnit;
import com.dcg.effect.Effect;
import com.dcg.game.Common;
import com.dcg.game.CreateEntity;
import com.dcg.location.Deck;
import java.util.List;

public class CreateCard extends CreateEntity {
  private final int cost;
  private int unitStrength = 0;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<Deck> mDeck;
  protected ComponentMapper<HasUnit> mHasUnit;
  protected ComponentMapper<Effect> mEffect;
  protected ComponentMapper<Common> mCommon;

  public CreateCard(String name, int cost) {
    super(name);
    this.cost = cost;
  }

  public CreateCard setUnit(int strength) {
    this.unitStrength = strength;
    return this;
  }

  // TODO: this implementation is currently not repeatedly invokable since it modifies state.
  @Override
  protected void run(int originEntity, List<Integer> targets, int value) {
    int cardEntity = createEntity(originEntity);
    Card card = mCard.create(cardEntity);
    card.cost = cost;
    mDeck.create(cardEntity);
    if (unitStrength > 0) {
      // Clone info and effects from this card into a CreateUnit command and make the that only
      // onEnter effect.
      Common cardCommon = mCommon.get(cardEntity);
      CreateUnit createUnit = new CreateUnit(cardCommon.name, unitStrength);
      createUnit.desc(cardCommon.description);

      Effect cardEffect = mEffect.get(cardEntity);
      cardEffect.onEnter.forEach(createUnit::addOnEnterEffects);
      cardEffect.onEnter.clear();
      cardEffect.onLeave.forEach(createUnit::addOnLeaveEffects);
      cardEffect.onLeave.clear();
      cardEffect.onCondition.forEach(createUnit::addOnConditionEffects);
      cardEffect.onCondition.clear();
      cardEffect.onEnter.add(createUnit);

      HasUnit hasUnit = mHasUnit.create(cardEntity);
      hasUnit.strength = unitStrength;
    }
  }
}

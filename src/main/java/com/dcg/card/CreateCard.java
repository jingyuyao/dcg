package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.battle.CreateUnit;
import com.dcg.command.CommandArgs;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;
import com.dcg.game.Owned;
import java.util.List;

public class CreateCard extends CreateEntity {
  private final int cost;
  private boolean canWrap = false;
  private CreateUnit createUnit;
  protected ComponentMapper<Owned> mOwned;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<HasUnit> mHasUnit;

  private CreateCard(String name, int cost) {
    super(name);
    this.cost = cost;
  }

  public static CreateCard basic(String name) {
    CreateCard createCard = new CreateCard(name, 0);
    createCard.tags(Basic.class);
    return createCard;
  }

  public static CreateCard spell(String name, int cost) {
    CreateCard createCard = new CreateCard(name, cost);
    createCard.tags(Spell.class);
    return createCard;
  }

  public static CreateCard unit(String name, int cost, int strength) {
    CreateCard createCard = new CreateCard(name, cost);
    createCard.hasUnit(strength);
    return createCard;
  }

  public CreateCard canWrap() {
    this.canWrap = true;
    return this;
  }

  /**
   * Mark this card as having a unit with the given strength. All effects added to the card will be
   * added to the unit instead.
   */
  public CreateCard hasUnit(int strength) {
    createUnit = CreateUnit.unitToken(name, strength);
    // NOTE: using super method here so the call won't be intercepted by our override.
    super.addOnEnterEffects(createUnit);
    return this;
  }

  @Override
  public CreateEntity addOnEnterEffects(CommandBuilder... effects) {
    if (createUnit != null) {
      createUnit.addOnEnterEffects(effects);
      return this;
    }
    return super.addOnEnterEffects(effects);
  }

  @Override
  public CreateEntity addOnLeaveEffects(CommandBuilder... effects) {
    if (createUnit != null) {
      createUnit.addOnLeaveEffects(effects);
      return this;
    }
    return super.addOnLeaveEffects(effects);
  }

  @Override
  public CreateEntity addOnConditionEffects(CommandBuilder... effects) {
    if (createUnit != null) {
      createUnit.addOnConditionEffects(effects);
      return this;
    }
    return super.addOnConditionEffects(effects);
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    int cardEntity = createEntity();
    if (originEntity != -1) {
      mOwned.create(cardEntity).owner = originEntity;
    }
    Card card = mCard.create(cardEntity);
    card.cost = cost;
    card.canWarp = canWrap;
    if (createUnit != null) {
      HasUnit hasUnit = mHasUnit.create(cardEntity);
      hasUnit.strength = createUnit.strength;
    }
  }
}

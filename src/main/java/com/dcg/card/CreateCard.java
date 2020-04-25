package com.dcg.card;

import com.artemis.ComponentMapper;
import com.dcg.battle.CreateUnit;
import com.dcg.command.CommandArgs;
import com.dcg.command.CommandBuilder;
import com.dcg.game.CreateEntity;
import java.util.List;

public class CreateCard extends CreateEntity {
  private final int cost;
  private CreateUnit createUnit;
  protected ComponentMapper<Card> mCard;
  protected ComponentMapper<HasUnit> mHasUnit;

  public CreateCard(String name, int cost) {
    super(name);
    this.cost = cost;
  }

  /**
   * Mark this card as having a unit with the given strength. All effects added to the card will be
   * added to the unit instead.
   */
  public CreateCard hasUnit(int strength) {
    createUnit = new CreateUnit(name, strength);
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
    int cardEntity = createEntity(originEntity);
    Card card = mCard.create(cardEntity);
    card.cost = cost;
    if (createUnit != null) {
      HasUnit hasUnit = mHasUnit.create(cardEntity);
      hasUnit.strength = createUnit.strength;
    }
  }
}

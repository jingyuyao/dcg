package com.dcg.condition;

import com.artemis.Aspect;
import com.dcg.card.Spell;
import com.dcg.game.CoreSystem;

public class AnySpell implements TriggerCondition {
  @Override
  public boolean test(CoreSystem coreSystem) {
    return coreSystem
        .getCurrentPlayerEntity()
        .flatMap(playerEntity -> coreSystem.getDescendants(playerEntity, Aspect.all(Spell.class)))
        .findAny()
        .isPresent();
  }
}

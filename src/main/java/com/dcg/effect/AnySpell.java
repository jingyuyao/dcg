package com.dcg.effect;

import com.artemis.Aspect;
import com.dcg.card.Spell;
import com.dcg.game.CoreSystem;
import java.util.function.BooleanSupplier;

public class AnySpell implements BooleanSupplier {
  protected CoreSystem coreSystem;

  @Override
  public boolean getAsBoolean() {
    return coreSystem
        .getCurrentPlayerEntity()
        .flatMap(playerEntity -> coreSystem.getDescendants(playerEntity, Aspect.all(Spell.class)))
        .findAny()
        .isPresent();
  }
}

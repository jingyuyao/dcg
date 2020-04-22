package com.dcg.condition;

import com.artemis.ComponentMapper;
import com.dcg.card.Spell;
import com.dcg.game.CoreSystem;

public class AnySpell implements TriggerCondition {
  protected ComponentMapper<Spell> mSpell;

  @Override
  public boolean test(CoreSystem coreSystem) {
    return coreSystem.getPlayArea().anyMatch(mSpell::has);
  }
}

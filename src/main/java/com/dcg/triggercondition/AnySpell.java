package com.dcg.triggercondition;

import com.artemis.ComponentMapper;
import com.dcg.card.Spell;
import com.dcg.game.CoreSystem;

public class AnySpell implements TriggerCondition {
  protected CoreSystem coreSystem;
  protected ComponentMapper<Spell> mSpell;

  @Override
  public boolean test(Integer originEntity) {
    return coreSystem.getPlayArea().anyMatch(mSpell::has);
  }
}

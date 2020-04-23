package com.dcg.triggercondition;

import com.artemis.ComponentMapper;
import com.dcg.card.Spell;
import com.dcg.game.CoreSystem;
import java.util.List;

public class AnySpell implements TriggerCondition {
  protected CoreSystem coreSystem;
  protected ComponentMapper<Spell> mSpell;

  @Override
  public boolean test(int originEntity, List<Integer> allowedTargets) {
    return coreSystem.getPlayArea().anyMatch(mSpell::has);
  }
}

package com.dcg.triggercondition;

import com.artemis.Aspect;
import com.artemis.Component;
import com.artemis.ComponentMapper;
import com.dcg.battle.Unit;
import com.dcg.game.CoreSystem;
import com.dcg.location.PlayArea;
import java.util.List;

/**
 * Checks whether the tag has been played. Does not count color from the card or unit this condition
 * is attached to.
 */
public class PlayedTag implements TriggerCondition {
  private final Class<? extends Component> tag;
  protected CoreSystem coreSystem;
  protected ComponentMapper<Unit> mUnit;

  public PlayedTag(Class<? extends Component> tag) {
    this.tag = tag;
  }

  @Override
  public boolean test(int originEntity, List<Integer> allowedTargets) {
    int originCardEntity =
        mUnit.has(originEntity) ? mUnit.get(originEntity).cardEntity : originEntity;
    return coreSystem
        .getStream(Aspect.all(PlayArea.class, tag))
        .anyMatch(cardEntity -> cardEntity != originCardEntity);
  }
}

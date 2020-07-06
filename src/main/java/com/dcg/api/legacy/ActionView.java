package com.dcg.api.legacy;

import com.dcg.action.Action;
import com.dcg.game.Common;
import java.util.List;

public class ActionView extends EntityView {
  public final boolean canTrigger;
  public final int minInputCount;
  public final int maxInputCount;
  public final List<Integer> allowedTargets;

  public ActionView(int id, Common common, Action action) {
    super(id, common);
    this.canTrigger = action.canTrigger;
    this.minInputCount = action.minInputCount;
    this.maxInputCount = action.maxInputCount;
    this.allowedTargets = action.allowedTargets;
  }
}

package com.dcg.turn;

import com.artemis.Component;
import com.artemis.annotations.EntityId;
import com.dcg.command.CommandBuilder;
import java.util.ArrayList;
import java.util.List;

public class Turn extends Component {
  @EntityId public int previousPlayerEntity = -1;
  public int powerPool = 0;
  /** Used by {@link com.dcg.effect.EffectSystem} to track triggered commands. */
  public List<CommandBuilder> triggeredConditionalEffects = new ArrayList<>();
}

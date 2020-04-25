package com.dcg.turn;

import com.artemis.Component;
import com.dcg.command.CommandBuilder;
import java.util.ArrayList;
import java.util.List;

public class Turn extends Component {
  public int powerPool;
  /** Used by {@link com.dcg.effect.EffectSystem} to track triggered commands. */
  public transient List<CommandBuilder> triggeredConditionalEffects = new ArrayList<>();
}

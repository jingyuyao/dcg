package com.dcg.player;

import com.artemis.Component;
import com.dcg.command.CommandBuilder;
import java.util.ArrayList;
import java.util.List;

public class Turn extends Component {
  public int powerPool = 0;
  /** Used by {@link com.dcg.effect.EffectSystem} to track triggered commands. */
  public List<CommandBuilder> triggeredConditionalEffects = new ArrayList<>();

  @Override
  public String toString() {
    return String.format("powerPool:%d", powerPool);
  }
}

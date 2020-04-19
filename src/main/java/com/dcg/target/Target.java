package com.dcg.target;

import java.util.List;

/** Marker type for command targets. */
public interface Target {
  /** Returns which entity is this target originated from. */
  int getOrigin();

  /** Returns a list of targeted entity. May contain {@link #getOrigin()} to target self. */
  List<Integer> getTargets();
}

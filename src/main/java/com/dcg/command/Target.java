package com.dcg.command;

import java.util.List;

/** Marker type for command targets. */
public interface Target {
  /** Returns which entity is this target is coming from. */
  int getFrom();

  /**
   * Returns a list of entity this target is going to. May contain {@link #getFrom()} to target
   * self.
   */
  List<Integer> getTo();
}

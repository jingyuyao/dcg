package com.dcg.command;

import com.artemis.World;

/** Stores some logic that can be executed later. */
public interface CommandBuilder {
  /**
   * Returns the executable interface for this command after it has been build for the given world
   * and sourceEntity.
   */
  Command build(World world, int sourceEntity);
}

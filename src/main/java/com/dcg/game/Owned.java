package com.dcg.game;

import com.artemis.Component;

/** Entities may or may not have this. If it does, it will be deleted when its owner is deleted. */
public class Owned extends Component {
  // NOTE: not using @EntityId so owner will not be automatically picked up during serialization.
  // For some reason the library authors decided @EntityId should trump `transient` keyword so we
  // can't even disable the auto reference feature without getting rid of @EntityId
  public int owner = -1;

  @Override
  public String toString() {
    return "*" + owner;
  }
}

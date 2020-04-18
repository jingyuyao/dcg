package com.dcg.game;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

/** Entities may or may not have this. If it does, it will be deleted when its owner is deleted. */
public class Owned extends Component {
  @EntityId public int owner = -1;

  @Override
  public String toString() {
    return "*" + owner;
  }
}

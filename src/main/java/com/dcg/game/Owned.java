package com.dcg.game;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

/** Entities may or may not have this. If it does, it will be removed when its owner is removed. */
public class Owned extends Component {
  @EntityId public int owner = -1;
}

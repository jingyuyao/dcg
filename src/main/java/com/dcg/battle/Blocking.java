package com.dcg.battle;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

public class Blocking extends Component {
  @EntityId public int attackingEntity = -1;
}

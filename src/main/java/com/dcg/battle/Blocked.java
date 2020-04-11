package com.dcg.battle;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

public class Blocked extends Component {
  @EntityId public int blockingEntity = -1;
}

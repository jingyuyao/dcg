package com.dcg.turn;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

public class Turn extends Component {
  @EntityId public int previousPlayerEntity = -1;
  public int powerPool = 0;

  @Override
  public String toString() {
    return String.format("powerPool:%d", powerPool);
  }
}

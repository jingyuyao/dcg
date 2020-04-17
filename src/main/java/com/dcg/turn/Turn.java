package com.dcg.turn;

import com.artemis.Component;

public class Turn extends Component {
  public int powerPool = 0;

  @Override
  public String toString() {
    return String.format("powerPool:%d", powerPool);
  }
}

package com.dcg.card;

import com.artemis.Component;

public class Strength extends Component {
  public int value = 1;

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}

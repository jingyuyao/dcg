package com.dcg.ownership;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

public class Owned extends Component {
  @EntityId public int owner = -1;

  @Override
  public String toString() {
    return "*" + owner;
  }
}

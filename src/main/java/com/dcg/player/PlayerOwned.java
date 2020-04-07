package com.dcg.player;

import com.artemis.Component;
import com.artemis.annotations.EntityId;

public class PlayerOwned extends Component {

  @EntityId
  public int playerEntity = -1;
}

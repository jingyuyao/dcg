package com.dcg.turn;

import com.artemis.Component;
import com.artemis.annotations.EntityId;
import com.dcg.command.Command;
import java.util.ArrayList;
import java.util.List;

public class Turn extends Component {
  @EntityId public int previousPlayerEntity = -1;
  public int powerPool = 0;
  public List<Command> commands = new ArrayList<>(5);
}

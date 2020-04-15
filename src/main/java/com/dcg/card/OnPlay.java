package com.dcg.card;

import com.artemis.Component;
import com.dcg.command.Command;
import java.util.ArrayList;
import java.util.List;

public class OnPlay extends Component {
  /** Effects happens immediately. */
  public List<Command> effects = new ArrayList<>(1);
  /** Actions are added to the pool to be executed by the player. */
  public List<Command> actions = new ArrayList<>(1);
}

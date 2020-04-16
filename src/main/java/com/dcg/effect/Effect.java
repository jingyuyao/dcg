package com.dcg.effect;

import com.artemis.Component;
import com.dcg.command.Command;
import java.util.Collections;
import java.util.List;

/**
 * Contains list of commands to be executed or turned into actions during various stages of the
 * attached entity's life cycle. Effect can be attached to players, cards or units.
 */
public class Effect extends Component {
  /** Commands to run when the associated entity is created. */
  public List<Command> onCreate = Collections.emptyList();
}

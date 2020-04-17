package com.dcg.effect;

import com.artemis.Component;
import com.dcg.command.CommandBuilder;
import java.util.Collections;
import java.util.List;

/**
 * Contains list of commands to be executed or turned into actions during various stages of the
 * attached entity's life cycle. Effect can be attached to players, cards or units.
 */
public class Effect extends Component {
  /** Commands to run when the associated entity is inserted into one of the activation tags. */
  public List<CommandBuilder> onEnter = Collections.emptyList();
  /** Commands to run when the associated entity is removed from one of the activation tags. */
  public List<CommandBuilder> onLeave = Collections.emptyList();
}

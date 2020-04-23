package com.dcg.action;

import com.artemis.Component;
import com.dcg.command.Command;
import java.util.Collections;
import java.util.List;

/**
 * Primary tag for an "action entity". Action entities stores a command and are owned by other
 * entities like player, card or unit. Actions can be queried and executed by a player.
 */
public class Action extends Component {
  public transient Command command;
  // TODO: surface world valid state so client can disable the input if conditions are not met.
  public int minInputCount = 0;
  public int maxInputCount = 0;
  public List<Integer> allowedTargets = Collections.emptyList();
}

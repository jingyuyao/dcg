package com.dcg.action;

import com.artemis.Component;
import com.dcg.command.Command;

/**
 * Primary tag for an "action entity". Action entities stores a command and are owned by other
 * entities like player, card or unit. Actions can be queried and executed by a player.
 */
public class Action extends Component {
  public Command command;

  @Override
  public String toString() {
    return command.toString();
  }
}

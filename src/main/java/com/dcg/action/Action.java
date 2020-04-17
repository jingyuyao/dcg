package com.dcg.action;

import com.artemis.Component;
import com.dcg.command.CommandBuilder;

/**
 * Primary tag for an "action entity". Action entities stores a command and are owned by other
 * entities like player, card or unit. Actions can be queried and executed by a player.
 */
public class Action extends Component {
  // TODO: make this executable version of the command
  public CommandBuilder command;
}

package com.dcg.command;

import java.util.List;

public interface Command {
  /** Returns the name of this command. It is the simple class name of the command builder. */
  String getName();

  /** Returns the name of this command. It is the simple class name of the command builder. */
  String getDescription(CommandData data);

  /** Returns the name of this command. It is the simple class name of the command builder. */
  boolean isClientVisible(CommandData data);

  /** Gets the entity associated with this command. The relationship is command dependent. */
  int getOriginEntity();

  /** Sets the optional user input for this command. */
  Command setInput(List<Integer> input);

  /** Returns the minimum number of inputs required. */
  int getMinInputCount();

  /** Returns the maximum number of inputs allowed. */
  int getMaxInputCount();

  /** Returns the list of allowed inputs. */
  List<Integer> getAllowedTargets();

  /**
   * Returns the data to be used to execute this command. This is a combination of static input data
   * and dynamic world data.
   */
  CommandData getData();

  /** Execute the logic for this command. */
  void run(CommandData data);

  /** Returns whether all the preconditions for this command are satisfied. */
  boolean canRun();

  /**
   * Returns whether the current input is valid for this command is satisfied. No side effects
   * allowed.
   */
  boolean isInputValid();

  /** Returns whether the command can be run in the current world state. No side effects allowed. */
  boolean canTrigger();
}

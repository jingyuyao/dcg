package com.dcg.command;

import java.util.List;

public interface Command {
  /** Sets the optional user input for this command. */
  Command setInputs(List<Integer> inputs);

  /** Returns the minimum number of inputs required. */
  int getMinInputCount();

  /** Returns the maximum number of inputs allowed. */
  int getMaxInputCount();

  /** Returns the list of allowed inputs. */
  List<Integer> getAllowedTargets();

  /** Execute the logic for this command. This must be repeatedly callable. */
  void run();

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

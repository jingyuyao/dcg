package com.dcg.player;

import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PerformActions extends Command {
  private final Integer[] actionIndexes;
  @Wire CommandChain commandChain;
  PlayerActionSystem playerActionSystem;

  public PerformActions(Integer... actionIndexes) {
    this.actionIndexes = actionIndexes;
  }

  @Override
  public void run() {
    List<Command> actions = playerActionSystem.getActions();
    List<Command> toPerform = new ArrayList<>(actionIndexes.length);
    for (int index : actionIndexes) {
      if (index >= 0 && index < actions.size()) {
        toPerform.add(actions.get(index));
      } else {
        System.out.println("Invalid actionIndex: " + index);
      }
    }
    // Use the vararg version so order is maintained.
    commandChain.addStart(toPerform.toArray(new Command[0]));
  }

  @Override
  public String toString() {
    return super.toString() + Arrays.asList(actionIndexes);
  }
}

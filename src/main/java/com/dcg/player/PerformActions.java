package com.dcg.player;

import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import java.util.ArrayList;
import java.util.List;

public class PerformActions extends Command {
  private final List<Integer> actionIndexes;
  @Wire CommandChain commandChain;
  PlayerActionSystem playerActionSystem;

  public PerformActions(List<Integer> actionIndexes) {
    this.actionIndexes = actionIndexes;
  }

  @Override
  public void run() {
    List<Command> actions = playerActionSystem.getActions();
    List<Command> toPerform = new ArrayList<>(actionIndexes.size());
    for (int index : actionIndexes) {
      if (index >= 0 && index < actions.size()) {
        toPerform.add(actions.get(index));
      } else {
        System.out.println("Invalid actionIndex: " + index);
      }
    }
    commandChain.addStart(toPerform);
  }

  @Override
  public String toString() {
    return super.toString() + actionIndexes;
  }
}

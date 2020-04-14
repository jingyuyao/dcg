package com.dcg.player;

import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import java.util.List;

public class PerformAction extends Command {
  private final int actionIndex;
  @Wire protected CommandChain commandChain;
  protected PlayerActionSystem playerActionSystem;

  public PerformAction(int actionIndex) {
    this.actionIndex = actionIndex;
  }

  @Override
  public void run() {
    List<Command> actions = playerActionSystem.getActions();
    try {
      Command command = actions.get(actionIndex);
      command.setInputs(getInputs());
      commandChain.addStart(command);
    } catch (IndexOutOfBoundsException e) {
      System.out.printf("Invalid actionIndex: %d\n", actionIndex);
    }
  }

  @Override
  public String toString() {
    return super.toString() + actionIndex;
  }
}

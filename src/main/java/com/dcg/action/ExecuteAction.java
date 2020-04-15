package com.dcg.action;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import java.util.List;

public class ExecuteAction extends Command {
  @Wire CommandChain commandChain;
  protected World world;
  protected ComponentMapper<Action> mAction;

  @Override
  public boolean canRun() {
    List<Integer> input = getInput();
    if (input.size() < 1) {
      System.out.println("    ExecuteAction requires at least one input.");
      return false;
    }

    int actionEntity = input.get(0);
    if (!mAction.has(actionEntity)) {
      System.out.printf("    *%d is not a valid action entity\n", actionEntity);
      return false;
    }

    Action action = mAction.get(actionEntity);
    world.inject(action.command);
    return action.command.canRun();
  }

  @Override
  public void run() {
    List<Integer> input = getInput();
    int actionEntity = input.get(0);
    Action action = mAction.get(actionEntity);
    List<Integer> inputPassThrough = input.subList(1, input.size());
    action.command.setInput(inputPassThrough);
    commandChain.addEnd(action.command);
    world.delete(actionEntity);
  }
}

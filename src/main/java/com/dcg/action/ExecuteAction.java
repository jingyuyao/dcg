package com.dcg.action;

import com.artemis.ComponentMapper;
import com.artemis.World;
import com.artemis.annotations.Wire;
import com.dcg.command.Command;
import com.dcg.command.CommandChain;
import com.dcg.command.CommandExecutor;
import java.util.List;

public class ExecuteAction extends Command {
  @Wire CommandChain commandChain;
  protected World world;
  protected ComponentMapper<Action> mAction;

  @Override
  protected boolean canRun() {
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

    List<Integer> inputPassThrough = input.subList(1, input.size());
    Action action = mAction.get(actionEntity);
    action.command.setInput(inputPassThrough);
    CommandExecutor commandExecutor = world.getInvocationStrategy();
    return commandExecutor.canExecute(action.command);
  }

  @Override
  protected void run() {
    List<Integer> input = getInput();
    List<Integer> inputPassThrough = input.subList(1, input.size());
    int actionEntity = input.get(0);
    Action action = mAction.get(actionEntity);
    action.command.setInput(inputPassThrough);
    commandChain.addEnd(action.command);
    world.delete(actionEntity);
  }
}

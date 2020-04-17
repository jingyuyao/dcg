package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Command;
import java.util.List;

public class ExecuteAction extends AbstractCommandBuilder {;

  protected ComponentMapper<Action> mAction;

  @Override
  protected boolean isInputValid() {
    if (input.size() < 1) {
      System.out.println("    ExecuteAction requires at least one input.");
      return false;
    }

    try {
      if (!input.stream().allMatch(entity -> world.getEntityManager().isActive(entity))) {
        System.out.println("    One of the input entities is not active");
        return false;
      }
    } catch (IndexOutOfBoundsException e) {
      System.out.printf("    *%s is not a valid entity\n", e.getMessage());
      return false;
    }

    int actionEntity = input.get(0);
    if (!mAction.has(actionEntity)) {
      System.out.printf("    *%d is not a valid action entity\n", actionEntity);
      return false;
    }

    List<Integer> inputPassThrough = input.subList(1, input.size());
    Action action = mAction.get(actionEntity);
    Command command = action.command.build(world, coreSystem.getOwner(actionEntity));
    command.setInput(inputPassThrough);
    return command.canRun();
  }

  @Override
  protected void run() {
    List<Integer> inputPassThrough = input.subList(1, input.size());
    int actionEntity = input.get(0);
    Action action = mAction.get(actionEntity);
    Command command = action.command.build(world, coreSystem.getOwner(actionEntity));
    command.setInput(inputPassThrough);
    commandChain.addEnd(command);
    world.delete(actionEntity);
  }
}

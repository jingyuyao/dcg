package com.dcg.action;

import com.artemis.ComponentMapper;
import com.artemis.annotations.Wire;
import com.dcg.command.CommandBase;
import com.dcg.command.CommandChain;
import com.dcg.command.ExecutableCommand;
import com.dcg.ownership.OwnershipSystem;
import java.util.List;

public class ExecuteAction extends CommandBase {
  @Wire protected CommandChain commandChain;
  protected OwnershipSystem ownershipSystem;
  protected ComponentMapper<Action> mAction;

  // TODO: require a single constructor specifying actionEntity, use inputs for pass through.

  @Override
  protected boolean isInputValid() {
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
    ExecutableCommand executableCommand =
        action.command.build(world, ownershipSystem.getOwner(actionEntity));
    executableCommand.setInput(inputPassThrough);
    return executableCommand.canRun();
  }

  @Override
  protected void run() {
    List<Integer> inputPassThrough = input.subList(1, input.size());
    int actionEntity = input.get(0);
    Action action = mAction.get(actionEntity);
    ExecutableCommand executableCommand =
        action.command.build(world, ownershipSystem.getOwner(actionEntity));
    executableCommand.setInput(inputPassThrough);
    commandChain.addEnd(executableCommand);
    world.delete(actionEntity);
  }
}

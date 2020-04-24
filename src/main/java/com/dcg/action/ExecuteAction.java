package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Command;
import com.dcg.command.CommandArgs;
import java.util.List;

public class ExecuteAction extends AbstractCommandBuilder {
  private final int actionEntity;
  private final List<Integer> inputs;
  protected ComponentMapper<Action> mAction;

  public ExecuteAction(int actionEntity, List<Integer> inputs) {
    this.actionEntity = actionEntity;
    this.inputs = inputs;
  }

  @Override
  protected void run(int originEntity, List<Integer> targets, CommandArgs args) {
    try {
      if (!world.getEntityManager().isActive(actionEntity) && mAction.has(actionEntity)) {
        System.out.printf("%d is not a valid action entity\n", actionEntity);
        return;
      }
      if (!inputs.stream().allMatch(entity -> world.getEntityManager().isActive(entity))) {
        System.out.println("Args contain an inactive entity");
        return;
      }
    } catch (IndexOutOfBoundsException e) {
      System.out.printf("Invalid entity ID: %s\n", e.getMessage());
      return;
    }

    Action action = mAction.get(actionEntity);
    Command command = action.command;
    command.setInput(inputs);
    if (command.canRun()) {
      commandChain.addEnd(command);
      world.delete(actionEntity);
    } else {
      System.out.printf("%s cannot run\n", command);
    }
  }
}

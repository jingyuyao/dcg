package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Command;
import com.dcg.target.Target;
import java.util.List;
import net.mostlyoriginal.api.utils.Preconditions;

public class ExecuteAction extends AbstractCommandBuilder {
  private final int actionEntity;
  private final List<Integer> inputs;
  protected ComponentMapper<Action> mAction;

  public ExecuteAction(int actionEntity, List<Integer> inputs) {
    this.actionEntity = actionEntity;
    this.inputs = inputs;
    addTargetConditions(
        target ->
            Preconditions.checkArgument(
                world.getEntityManager().isActive(actionEntity) && mAction.has(actionEntity),
                "Action entity not active"),
        target ->
            Preconditions.checkArgument(
                inputs.stream().allMatch(entity -> world.getEntityManager().isActive(entity)),
                "Input entity not active"),
        target -> {
          Action action = mAction.get(actionEntity);
          Command command = action.command;
          command.setInputs(inputs);
          Preconditions.checkArgument(command.canRun(), "Command cannot run");
        });
  }

  @Override
  protected void run(Target target) {
    Action action = mAction.get(actionEntity);
    Command command = action.command;
    command.setInputs(inputs);
    commandChain.addEnd(command);
    world.delete(actionEntity);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), coreSystem.toName(actionEntity));
  }
}

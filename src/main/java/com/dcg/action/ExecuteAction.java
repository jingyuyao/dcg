package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Command;
import com.dcg.target.Inputs;
import com.dcg.target.Target;
import java.util.List;

public class ExecuteAction extends AbstractCommandBuilder {
  private final int actionEntity;
  private final List<Integer> inputs;
  protected ComponentMapper<Action> mAction;

  public ExecuteAction(int actionEntity, List<Integer> inputs) {
    this.actionEntity = actionEntity;
    this.inputs = inputs;
    setTargetFunction(new Inputs());
    addWorldConditions(
        coreSystem -> {
          try {
            return world.getEntityManager().isActive(actionEntity) && mAction.has(actionEntity);
          } catch (IndexOutOfBoundsException e) {
            return false;
          }
        },
        coreSystem -> {
          try {
            return inputs.stream().allMatch(entity -> world.getEntityManager().isActive(entity));
          } catch (IndexOutOfBoundsException e) {
            return false;
          }
        });
    addTargetConditions(
        target -> {
          Action action = mAction.get(actionEntity);
          Command command = action.command;
          command.setInputs(inputs);
          return command.canRun();
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

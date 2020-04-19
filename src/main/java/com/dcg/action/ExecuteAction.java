package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Command;
import com.dcg.command.Input;
import com.dcg.target.Inputs;
import com.dcg.target.Target;

public class ExecuteAction extends AbstractCommandBuilder {
  private final int actionEntity;
  private final Input input;
  protected ComponentMapper<Action> mAction;

  public ExecuteAction(int actionEntity, Input input) {
    this.actionEntity = actionEntity;
    this.input = input;
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
            return !input.get().isPresent()
                || world.getEntityManager().isActive(input.get().getAsInt());
          } catch (IndexOutOfBoundsException e) {
            return false;
          }
        });
    addTargetConditions(
        target -> {
          Action action = mAction.get(actionEntity);
          Command command = action.command;
          command.setInput(input);
          return command.canRun();
        });
  }

  @Override
  protected void run(Target target) {
    Action action = mAction.get(actionEntity);
    Command command = action.command;
    command.setInput(input);
    commandChain.addEnd(command);
    world.delete(actionEntity);
  }

  @Override
  public String toString() {
    return String.format("%s %s", super.toString(), coreSystem.toName(actionEntity));
  }
}

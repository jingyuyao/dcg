package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Command;
import com.dcg.command.Target;
import com.dcg.targetsource.Inputs;

public class ExecuteAction extends AbstractCommandBuilder {;
  protected ComponentMapper<Action> mAction;

  public ExecuteAction() {
    setTargetSource(new Inputs());
    addTargetConditions(
        target -> {
          try {
            return target.get().stream()
                .allMatch(entity -> world.getEntityManager().isActive(entity));
          } catch (IndexOutOfBoundsException e) {
            return false;
          }
        },
        target -> mAction.has(target.get().get(0)),
        target -> {
          Action action = mAction.get(target.get().get(0));
          Command command = action.command;
          command.setInput(() -> target.get().subList(1, target.get().size()));
          return command.canRun();
        });
  }

  @Override
  protected void run(Target target) {
    int actionEntity = target.get().get(0);
    Action action = mAction.get(actionEntity);
    Command command = action.command;
    command.setInput(() -> target.get().subList(1, target.get().size()));
    commandChain.addEnd(command);
    world.delete(actionEntity);
  }
}

package com.dcg.action;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.command.Command;
import com.dcg.command.CommandData;
import com.dcg.player.Player;
import java.util.List;

public class ExecuteAction extends AbstractCommandBuilder {
  private final int actionEntity;
  private final List<Integer> inputs;
  protected ComponentMapper<Action> mAction;
  protected ComponentMapper<Player> mPlayer;

  public ExecuteAction(int actionEntity, List<Integer> inputs) {
    this.actionEntity = actionEntity;
    this.inputs = inputs;
    // Validate as a trigger condition so bad input is rejected before the command is logged, since
    // logging reads the action.
    addTriggerConditions(
        (originEntity, allowedTargets) -> {
          if (originEntity != coreSystem.getCurrentPlayerEntity()) {
            System.out.printf("%d is not the current player\n", originEntity);
            return false;
          }
          if (!coreSystem.isActive(actionEntity) || !mAction.has(actionEntity)) {
            System.out.printf("%d is not a valid action entity\n", actionEntity);
            return false;
          }
          // Actions on unowned cards like the forge row are shared. Everything else must belong to
          // the player.
          int ownerEntity = coreSystem.getRoot(actionEntity);
          if (ownerEntity != originEntity && mPlayer.has(ownerEntity)) {
            System.out.printf("%d does not own action %d\n", originEntity, actionEntity);
            return false;
          }
          if (!inputs.stream().allMatch(entity -> entity != null && coreSystem.isActive(entity))) {
            System.out.println("Args contain an invalid entity");
            return false;
          }
          return true;
        });
  }

  @Override
  protected void run(CommandData data) {
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

  @Override
  protected String getDescription(CommandData data) {
    Action action = mAction.get(actionEntity);
    Command command = action.command;
    return String.format("%s", command.getName());
  }
}

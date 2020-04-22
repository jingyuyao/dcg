package com.dcg.battle;

import com.artemis.ComponentMapper;
import com.dcg.command.AbstractCommandBuilder;
import com.dcg.target.AttackingUnitInputs;
import com.dcg.target.Target;
import net.mostlyoriginal.api.utils.Preconditions;

public class Block extends AbstractCommandBuilder {
  protected ComponentMapper<Unit> mUnit;

  public Block() {
    setTargetSource(new AttackingUnitInputs());
    addTargetConditions(
        target ->
            Preconditions.checkArgument(
                target.getTargets().size() == 1, "Block requires one target"),
        target ->
            Preconditions.checkArgument(
                coreSystem.getAttackingEntities().anyMatch(e -> e == getAttackingEntity(target)),
                "Target is not attacking"),
        target ->
            Preconditions.checkArgument(
                !mUnit.get(getAttackingEntity(target)).unblockable, "Target is unblockable"),
        target ->
            Preconditions.checkArgument(
                !mUnit.get(getAttackingEntity(target)).flying
                    || mUnit.get(getDefendingEntity(target)).flying,
                "Target is flying but defender is not"),
        target -> {
          Unit defendingUnit = mUnit.get(getDefendingEntity(target));
          Preconditions.checkArgument(
              defendingUnit.strength + defendingUnit.defense
                  >= mUnit.get(getAttackingEntity(target)).strength,
              "Target strength is greater than defender");
        });
  }

  private int getDefendingEntity(Target target) {
    return target.getOrigin();
  }

  private int getAttackingEntity(Target target) {
    return target.getTargets().get(0);
  }

  @Override
  protected void run(Target target) {
    commandChain.addEnd(new DestroyUnit().build(world, getAttackingEntity(target)));
    Unit blockingUnit = mUnit.get(getDefendingEntity(target));
    if (!blockingUnit.endurance) {
      commandChain.addEnd(new DestroyUnit().build(world, getDefendingEntity(target)));
    }
  }
}

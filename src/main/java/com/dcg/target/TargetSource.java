package com.dcg.target;

import java.util.Collections;
import java.util.List;

/** A function to select the target of the command given originating source entity and input. */
public abstract class TargetSource {
  public Target get(int originEntity, List<Integer> input) {
    return this.new TargetImpl(originEntity, input);
  }

  /** Override to returns the minimum number of inputs required. */
  public int getMinInputCount() {
    return 0;
  }

  /** Override to returns the maximum number of inputs allowed. */
  public int getMaxInputCount() {
    return 0;
  }

  /** Override to returns the list of allowed inputs. */
  public List<Integer> getAllowedInputs() {
    return Collections.emptyList();
  }

  /** Transforms the given origin entity and input into a list of targets. */
  protected abstract List<Integer> transform(int originEntity, List<Integer> input);

  private class TargetImpl implements Target {
    private final int originEntity;
    private final List<Integer> input;

    private TargetImpl(int originEntity, List<Integer> input) {
      this.originEntity = originEntity;
      this.input = input;
    }

    @Override
    public int getOrigin() {
      return originEntity;
    }

    @Override
    public List<Integer> getTargets() {
      return transform(originEntity, input);
    }
  }
}

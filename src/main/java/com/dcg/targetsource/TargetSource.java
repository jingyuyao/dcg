package com.dcg.targetsource;

import java.util.List;

// TODO: move max target count here so its overridable, make this an abstract class with
// setMaxTargetCount
/** Interface providing logic to get targets for an effect. Instances will be injected. */
public interface TargetSource {
  List<Integer> get(int sourceEntity, List<Integer> input);
}

package com.dcg.effect;

import java.util.List;

/** Interface providing logic to get targets for an effect. Instances will be injected. */
public interface TargetSource {
  List<Integer> get(int sourceEntity, List<Integer> input);
}

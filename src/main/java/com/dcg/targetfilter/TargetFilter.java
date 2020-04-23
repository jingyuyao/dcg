package com.dcg.targetfilter;

@FunctionalInterface
public interface TargetFilter {
  boolean test(int originEntity, int target);
}

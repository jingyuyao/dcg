package com.dcg.target;

import com.artemis.World;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Represents a source of targets that may optionally be filtered. */
public abstract class TargetSource {
  protected World world;
  private final List<TargetFilter> filters = new ArrayList<>();

  public TargetSource addFilters(TargetFilter... filters) {
    Collections.addAll(this.filters, filters);
    return this;
  }

  /** Returns the list of allowed inputs. */
  public List<Integer> getAllowedTargets(int originEntity) {
    Stream<Integer> source = getSource(originEntity);
    for (TargetFilter filter : filters) {
      world.inject(filter);
      source = filter.apply(originEntity, source);
    }
    return source.collect(Collectors.toList());
  }

  /** Override to provide the source stream of unfiltered targets. */
  protected abstract Stream<Integer> getSource(int originEntity);
}

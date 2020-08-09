package com.dcg.targetsource;

import com.artemis.World;
import com.dcg.targetfilter.TargetFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/** Represents a source of targets that may optionally be filtered. */
public abstract class TargetSource {
  protected World world;
  private final List<TargetFilter> filters = new ArrayList<>();

  public static TargetSource of(int targetEntity) {
    return new TargetSource() {
      @Override
      protected Stream<Integer> getSource(int originEntity) {
        return Stream.of(targetEntity);
      }
    };
  }

  public TargetSource addFilters(TargetFilter... filters) {
    Collections.addAll(this.filters, filters);
    return this;
  }

  /** Returns the list of allowed inputs. */
  public List<Integer> getAllowedTargets(int originEntity) {
    return getSource(originEntity)
        .filter(
            entity ->
                filters.stream()
                    .peek(world::inject)
                    .allMatch(filter -> filter.test(originEntity, entity)))
        .collect(Collectors.toList());
  }

  /** Override to provide the source stream of unfiltered targets. */
  protected abstract Stream<Integer> getSource(int originEntity);
}

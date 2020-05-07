package com.dcg.game;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Util {
  /** Shuffle the input list and return a maximum of {@code count} unique elements from it. */
  public static <T> List<T> pickRandom(List<T> source, int count) {
    Collections.shuffle(source);
    return source.stream().limit(count).collect(Collectors.toList());
  }
}

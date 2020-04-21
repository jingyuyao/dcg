package com.dcg.condition;

import com.dcg.game.CoreSystem;
import java.util.function.Predicate;

/** A predicate that tests the world. Instances will be injected. */
public interface TriggerCondition extends Predicate<CoreSystem> {}

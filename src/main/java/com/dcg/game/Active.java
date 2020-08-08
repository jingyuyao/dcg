package com.dcg.game;

import com.artemis.Component;

/**
 * <p>Marker for an entity that is 'active' in the game. This tag is removed to indicate the entity
 * is no longer part of the game. The entities are never deleted to support looking up 'removed'
 * entities at any point of the game, especially in logs.
 *
 * <p>Note: an 'Active' tag is used as opposed to using a 'Removed' tag to force call sites to more
 * consciously include it as part of its declaration, i.e. @All(Active.class, Unit.class, ...)
 * as opposed to @All(Unit.class) & @Exclude(Removed.class)
 */
public class Active extends Component {}

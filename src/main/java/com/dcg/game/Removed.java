package com.dcg.game;

import com.artemis.Component;

/**
 * Marker for an entity that is 'removed' from the game. The actual entity itself is kept in the
 * world to support log history look up and referencing 'removed' entities by entities that are
 * still alive.
 */
public class Removed extends Component {}

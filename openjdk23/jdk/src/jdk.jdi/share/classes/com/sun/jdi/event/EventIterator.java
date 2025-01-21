/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package com.sun.jdi.event;

import java.util.Iterator;

/**
 * EventIterators are unmodifiable.
 *
 * @see Event
 * @see EventSet
 * @see EventSet#iterator
 *
 * @author Robert Field
 * @since  1.3
 */

public interface EventIterator extends Iterator<Event> {

    /**
     * @return The next {@link Event} in an {@link EventSet}.
     */
    Event nextEvent();
}

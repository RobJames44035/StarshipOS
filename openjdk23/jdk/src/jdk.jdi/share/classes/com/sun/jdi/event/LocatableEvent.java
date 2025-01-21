/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package com.sun.jdi.event;

import com.sun.jdi.Locatable;
import com.sun.jdi.ThreadReference;
/**
 * Abstract superinterface of events which have both location
 * and thread.
 *
 * @author Robert Field
 * @since  1.3
 */
public interface LocatableEvent extends Event, Locatable {

    /**
     * Returns the thread in which this event has occurred.
     *
     * @return a {@link ThreadReference} which mirrors the event's thread in
     * the target VM.
     */
    public ThreadReference thread();
}

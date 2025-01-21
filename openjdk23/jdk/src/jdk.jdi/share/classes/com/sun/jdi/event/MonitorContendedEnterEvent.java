/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package com.sun.jdi.event;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.ThreadReference;

/**
 *
 * Notification that a thread in the target VM is attempting
 * to enter a monitor that is already acquired by another thread.
 *
 * @see EventQueue
 * @see MonitorContendedEnteredEvent
 *
 *
 * @author Swamy Venkataramanappa
 * @since  1.6
 */
public interface MonitorContendedEnterEvent extends LocatableEvent {

    /**
     * Returns the thread in which this event has occurred.
     *
     * @return a {@link ThreadReference} which mirrors the event's thread in
     * the target VM.
     */
    public ThreadReference thread();

    /**
     * Returns the method that was entered.
     *
     * @return an {@link ObjectReference} for the monitor.
     */
    public ObjectReference monitor();
}

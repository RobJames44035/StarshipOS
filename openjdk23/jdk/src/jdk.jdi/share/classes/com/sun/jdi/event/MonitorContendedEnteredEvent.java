/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package com.sun.jdi.event;

import com.sun.jdi.ObjectReference;
import com.sun.jdi.ThreadReference;

/**
 *
 *  Notification that a thread in the target VM is entering a monitor
 *  after waiting for it to be released by another thread.
 *
 * @see EventQueue
 * @see MonitorContendedEnterEvent
 *
 * @author Swamy Venkataramanappa
 * @since  1.6
 */
public interface MonitorContendedEnteredEvent extends LocatableEvent {

    /**
     * Returns the thread in which this event has occurred.
     *
     * @return a {@link ThreadReference} which mirrors the event's thread in
     * the target VM.
     */
    public ThreadReference thread();

    /**
     * Returns the monitor that was entered.
     *
     * @return an {@link ObjectReference} for the monitor.
     */
    public ObjectReference monitor();
}

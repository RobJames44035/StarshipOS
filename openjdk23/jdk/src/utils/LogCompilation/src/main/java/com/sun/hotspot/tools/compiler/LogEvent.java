/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package com.sun.hotspot.tools.compiler;

import java.io.PrintStream;

/**
 * The interface of an event from a HotSpot compilation log. Events can have a
 * duration, e.g., a compiler {@link Phase} is an event, and so is an entire
 * {@link Compilation}.
 */
public interface LogEvent {

    /**
     * The event's start time.
     */
    public double getStart();

    /**
     * The event's duration in milliseconds.
     */
    public double getElapsedTime();

    /**
     * The compilation during which this event was signalled.
     */
    public Compilation getCompilation();

    /**
     * Print the event to the given stream.
     */
    public void print(PrintStream stream, boolean printID);
}

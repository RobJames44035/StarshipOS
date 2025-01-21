/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.jfr.events;

import jdk.jfr.Category;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.Timespan;
import jdk.jfr.internal.MirrorEvent;

@Category("Java Application")
@Label("Java Thread Sleep")
@Name("jdk.ThreadSleep")
@StackFilter({"java.lang.Thread::afterSleep",
              "java.lang.Thread::sleepNanos",
              "java.lang.Thread::sleep"})
public final class ThreadSleepEvent extends MirrorEvent {
    @Label("Sleep Time")
    @Timespan(Timespan.NANOSECONDS)
    public long time;
}

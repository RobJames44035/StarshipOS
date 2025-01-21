/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.jfr.events;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.internal.MirrorEvent;
import jdk.jfr.internal.RemoveFields;

@Category({"Operating System"})
@Label("Process Start")
@Name("jdk.ProcessStart")
@Description("Operating system process started")
@RemoveFields("duration")
@StackFilter({"java.lang.ProcessBuilder", "java.lang.Runtime::exec"})
public final class ProcessStartEvent extends MirrorEvent {
    @Label("Process Id")
    public long pid;

    @Label("Directory")
    public String directory;

    @Label("Command")
    public String command;
}

/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package jdk.jfr.events;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.internal.MirrorEvent;
import jdk.jfr.internal.RemoveFields;

@Category({"Java Development Kit", "Threading"})
@Label("Virtual Thread Submit Failed")
@Name("jdk.VirtualThreadSubmitFailed")
@Description("Submit of task for virtual thread failed")
@RemoveFields("duration")
public final class VirtualThreadSubmitFailedEvent extends MirrorEvent {

    @Label("Thread Id")
    public long javaThreadId;

    @Label("Exception Message")
    public String exceptionMessage;

}

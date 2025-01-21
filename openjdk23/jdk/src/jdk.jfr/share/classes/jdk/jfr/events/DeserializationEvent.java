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

@Category({"Java Development Kit", "Serialization"})
@Label("Deserialization")
@Name("jdk.Deserialization")
@Description("Results of deserialization and ObjectInputFilter checks")
@RemoveFields("duration")
public final class DeserializationEvent extends MirrorEvent {

    @Label("Filter Configured")
    public boolean filterConfigured;

    @Label("Filter Status")
    public String filterStatus;

    @Label ("Type")
    public Class<?> type;

    @Label ("Array Length")
    public int arrayLength;

    @Label ("Object References")
    public long objectReferences;

    @Label ("Depth")
    public long depth;

    @Label ("Bytes Read")
    public long bytesRead;

    @Label ("Exception Type")
    public Class<?> exceptionType;

    @Label ("Exception Message")
    public String exceptionMessage;
}

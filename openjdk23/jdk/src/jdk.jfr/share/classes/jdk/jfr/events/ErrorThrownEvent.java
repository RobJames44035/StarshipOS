/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */
package jdk.jfr.events;

import jdk.jfr.Category;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.internal.MirrorEvent;
import jdk.jfr.internal.RemoveFields;
import jdk.jfr.internal.Type;

@Name(Type.EVENT_NAME_PREFIX + "JavaErrorThrow")
@Label("Java Error")
@Category("Java Application")
@Description("An object derived from java.lang.Error has been created. OutOfMemoryErrors are ignored")
@RemoveFields("duration")
public final class ErrorThrownEvent extends MirrorEvent {

    @Label("Message")
    public String message;

    @Label("Class")
    public Class<?> thrownClass;}

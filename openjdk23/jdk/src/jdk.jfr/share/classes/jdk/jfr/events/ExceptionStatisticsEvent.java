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

@Name(Type.EVENT_NAME_PREFIX + "ExceptionStatistics")
@Label("Exception Statistics")
@Category({ "Java Application", "Statistics" })
@Description("Number of objects derived from java.lang.Throwable that have been created")
@RemoveFields({"duration", "eventThread", "stackTrace"})
public final class ExceptionStatisticsEvent extends MirrorEvent {

    @Label("Exceptions Created")
    public long throwables;
}

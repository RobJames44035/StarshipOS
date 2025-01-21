/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.events;

import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.Registered;
import jdk.jfr.StackTrace;

@Registered(false)
@Enabled(false)
@StackTrace(false)
abstract class AbstractJDKEvent extends Event {
}

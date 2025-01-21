/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.api.metadata.eventtype;

import jdk.jfr.Enabled;
import jdk.jfr.Event;
import jdk.jfr.Name;
import jdk.jfr.Period;
import jdk.jfr.SettingDefinition;
import jdk.jfr.StackTrace;
import jdk.jfr.Threshold;
import jdk.test.lib.jfr.SimpleSetting;

@Period("10 s")
@Threshold("100 ms")
@StackTrace(true)
@Enabled(false)
public class EventWithCustomSettings extends Event {
    @SettingDefinition
    @Name("setting1")
    boolean methodNameNotUsed(SimpleSetting cs) {
        return true;
    }

    @SettingDefinition
    boolean setting2(SimpleSetting cs) {
        return true;
    }
}

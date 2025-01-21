/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.events;

import jdk.jfr.Category;
import jdk.jfr.Label;
import jdk.jfr.Name;
import jdk.jfr.internal.RemoveFields;
import jdk.jfr.internal.Type;

@Name(Type.EVENT_NAME_PREFIX + "ActiveSetting")
@Label("Recording Setting")
@Category("Flight Recorder")
@RemoveFields({"duration", "eventThread", "stackTrace"})
public final class ActiveSettingEvent extends AbstractJDKEvent {

    // The order of these fields must be the same as the parameters in
    // commit(... , long, String, String)

    @Label("Event Id")
    public long id;

    @Label("Setting Name")
    public String name;

    @Label("Setting Value")
    public String value;

    public static void commit(long startTime, long id, String name, String value) {
        // Generated
    }

    public static boolean enabled() {
        return false; // Generated
    }
}

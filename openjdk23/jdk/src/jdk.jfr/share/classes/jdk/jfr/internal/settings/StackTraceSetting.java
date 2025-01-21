/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.internal.settings;

import jdk.jfr.BooleanFlag;
import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.MetadataDefinition;
import jdk.jfr.Name;
import jdk.jfr.internal.PlatformEventType;
import jdk.jfr.internal.Type;

@MetadataDefinition
@Label("Stack Trace")
@Name(Type.SETTINGS_PREFIX + "StackTrace")
@Description("Record stack traces")
@BooleanFlag
public final class StackTraceSetting extends BooleanSetting {
    private static final long typeId = Type.getTypeId(StackTraceSetting.class);

    public StackTraceSetting(PlatformEventType eventType, String defaultValue) {
        super(eventType, defaultValue);
    }

    @Override
    protected void apply(PlatformEventType eventType, boolean value) {
        eventType.setStackTraceEnabled(value);
    }

    public static boolean isType(long typeId) {
        return StackTraceSetting.typeId == typeId;
    }
}

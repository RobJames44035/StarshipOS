/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.internal.settings;

import jdk.jfr.Description;
import jdk.jfr.BooleanFlag;
import jdk.jfr.Label;
import jdk.jfr.MetadataDefinition;
import jdk.jfr.Name;
import jdk.jfr.internal.PlatformEventType;
import jdk.jfr.internal.Type;

@MetadataDefinition
@Label("Enabled")
@Description("Record event")
@Name(Type.SETTINGS_PREFIX + "Enabled")
@BooleanFlag
public final class EnabledSetting extends BooleanSetting {
    public EnabledSetting(PlatformEventType eventType, String defaultValue) {
        super(eventType, defaultValue);
    }

    @Override
    protected void apply(PlatformEventType eventType, boolean value) {
        eventType.setEnabled(value);
        if (eventType.isEnabled() && !eventType.isJVM()) {
            if (!eventType.isInstrumented()) {
                eventType.markForInstrumentation(true);
            }
        }
    }
}

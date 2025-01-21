/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.internal.settings;

import static jdk.jfr.internal.util.ValueParser.MISSING;

import java.util.Objects;
import java.util.Set;

import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.MetadataDefinition;
import jdk.jfr.Name;
import jdk.jfr.SettingControl;
import jdk.jfr.Timespan;
import jdk.jfr.internal.PlatformEventType;
import jdk.jfr.internal.Type;
import jdk.jfr.internal.util.ValueParser;

@MetadataDefinition
@Label("Threshold")
@Name(Type.SETTINGS_PREFIX + "Threshold")
@Description("Record event with duration above or equal to threshold")
@Timespan
public final class ThresholdSetting extends SettingControl {
    public static final String DEFAULT_VALUE = "0 ns";
    private static final long typeId = Type.getTypeId(ThresholdSetting.class);
    private String value = DEFAULT_VALUE;
    private final PlatformEventType eventType;

    public ThresholdSetting(PlatformEventType eventType) {
       this.eventType = Objects.requireNonNull(eventType);
    }

    @Override
    public String combine(Set<String> values) {
        Long min = null;
        String text = null;
        for (String value : values) {
            long nanos = ValueParser.parseTimespanWithInfinity(value, MISSING);
            if (nanos != MISSING) {
                if (min == null || nanos < min) {
                    text = value;
                    min = nanos;
                }
            }
        }
        return Objects.requireNonNullElse(text, DEFAULT_VALUE);
    }

    @Override
    public void setValue(String value) {
        long nanos = ValueParser.parseTimespanWithInfinity(value, MISSING);
        if (nanos != MISSING) {
            eventType.setThreshold(nanos);
            this.value = value;
        }
    }

    @Override
    public String getValue() {
        return value;
    }

    public static boolean isType(long typeId) {
        return ThresholdSetting.typeId == typeId;
    }
}

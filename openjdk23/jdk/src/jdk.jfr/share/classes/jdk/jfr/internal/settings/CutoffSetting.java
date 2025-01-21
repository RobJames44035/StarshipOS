/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

package jdk.jfr.internal.settings;

import static jdk.jfr.internal.util.ValueParser.MISSING;

import java.util.Objects;
import java.util.Set;

import jdk.jfr.Description;
import jdk.jfr.SettingControl;
import jdk.jfr.Label;
import jdk.jfr.MetadataDefinition;
import jdk.jfr.Name;
import jdk.jfr.Timespan;
import jdk.jfr.internal.PlatformEventType;
import jdk.jfr.internal.Type;
import jdk.jfr.internal.util.ValueParser;

@MetadataDefinition
@Label("Cutoff")
@Description("Limit running time of event")
@Name(Type.SETTINGS_PREFIX + "Cutoff")
@Timespan
public final class CutoffSetting extends SettingControl {
    public static final String DEFAULT_VALUE = ValueParser.INFINITY;
    private String value = DEFAULT_VALUE;
    private final PlatformEventType eventType;

    public CutoffSetting(PlatformEventType eventType) {
       this.eventType = Objects.requireNonNull(eventType);
    }

    @Override
    public String combine(Set<String> values) {
        long max = 0;
        String text = null;
        for (String value : values) {
            long nanos = ValueParser.parseTimespanWithInfinity(value, MISSING);
            if (nanos != MISSING && nanos > max) {
                text = value;
                max = nanos;
            }
        }
        return Objects.requireNonNullElse(text, DEFAULT_VALUE);
    }

    @Override
    public void setValue(String value) {
        long nanos = ValueParser.parseTimespanWithInfinity(value, MISSING);
        if (nanos != MISSING) {
            eventType.setCutoff(nanos);
            this.value = value;
        }
    }

    @Override
    public String getValue() {
        return value;
    }

    public static long parseValueSafe(String value) {
        if (value == null) {
            return 0L;
        }
        return ValueParser.parseTimespanWithInfinity(value, 0L);
    }
}

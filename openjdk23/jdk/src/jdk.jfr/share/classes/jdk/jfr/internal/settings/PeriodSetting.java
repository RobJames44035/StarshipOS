/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.internal.settings;

import java.util.Objects;
import java.util.Set;

import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.MetadataDefinition;
import jdk.jfr.Name;
import jdk.jfr.SettingControl;
import jdk.jfr.internal.PlatformEventType;
import jdk.jfr.internal.Type;
import jdk.jfr.internal.util.ValueParser;
import static jdk.jfr.internal.util.ValueParser.MISSING;

@MetadataDefinition
@Label("Period")
@Description("Record event at interval")
@Name(Type.SETTINGS_PREFIX + "Period")
public final class PeriodSetting extends SettingControl {
    private static final long typeId = Type.getTypeId(PeriodSetting.class);

    public static final String EVERY_CHUNK = "everyChunk";
    public static final String BEGIN_CHUNK = "beginChunk";
    public static final String END_CHUNK = "endChunk";
    public static final String DEFAULT_VALUE = EVERY_CHUNK;
    public static final String NAME = "period";
    private final PlatformEventType eventType;
    private String value = EVERY_CHUNK;

    public PeriodSetting(PlatformEventType eventType) {
        this.eventType = Objects.requireNonNull(eventType);
    }

    @Override
    public String combine(Set<String> values) {
        boolean beginChunk = false;
        boolean endChunk = false;
        Long min = null;
        String text = null;
        for (String value : values) {
            switch (value) {
            case EVERY_CHUNK:
                beginChunk = true;
                endChunk = true;
                break;
            case BEGIN_CHUNK:
                beginChunk = true;
                break;
            case END_CHUNK:
                endChunk = true;
                break;
            default:
                long nanos = ValueParser.parseTimespanWithInfinity(value, MISSING);
                if (nanos != MISSING) {
                    if (min == null || nanos < min) {
                        text = value;
                        min = nanos;
                    }
                }
            }
        }
        // A specified interval trumps *_CHUNK
        if (min != null) {
            return text;
        }
        if (beginChunk && !endChunk) {
            return BEGIN_CHUNK;
        }
        if (!beginChunk && endChunk) {
            return END_CHUNK;
        }
        return DEFAULT_VALUE; // "everyChunk" is default
    }

    @Override
    public void setValue(String value) {
        switch (value) {
        case EVERY_CHUNK:
            eventType.setPeriod(0, true, true);
            break;
        case BEGIN_CHUNK:
            eventType.setPeriod(0, true, false);
            break;
        case END_CHUNK:
            eventType.setPeriod(0, false, true);
            break;
        default:
            long nanos = ValueParser.parseTimespanWithInfinity(value, MISSING);
            if (nanos == MISSING) {
                return;
            }
            if (nanos == 0 || nanos == Long.MAX_VALUE) {
                eventType.setPeriod(nanos, false, false);
            } else {
                eventType.setPeriod(Math.max(1, nanos / 1_000_000), false, false);
            }
        }
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

    public static boolean isType(long typeId) {
        return PeriodSetting.typeId == typeId;
    }
}

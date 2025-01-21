/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

package jdk.jfr.internal.settings;

import static jdk.jfr.internal.util.TimespanUnit.SECONDS;
import static jdk.jfr.internal.util.TimespanUnit.MILLISECONDS;

import java.util.Objects;
import java.util.Set;

import jdk.jfr.Description;
import jdk.jfr.Label;
import jdk.jfr.MetadataDefinition;
import jdk.jfr.Name;
import jdk.jfr.SettingControl;
import jdk.jfr.internal.PlatformEventType;
import jdk.jfr.internal.Throttle;
import jdk.jfr.internal.Type;
import jdk.jfr.internal.util.Rate;
import jdk.jfr.internal.util.TimespanUnit;
import jdk.jfr.internal.util.Utils;

@MetadataDefinition
@Label("Throttle")
@Description("Throttles the emission rate for an event")
@Name(Type.SETTINGS_PREFIX + "Throttle")
public final class ThrottleSetting extends SettingControl {
    public static final String DEFAULT_VALUE = Throttle.DEFAULT;
    private final PlatformEventType eventType;
    private String value = DEFAULT_VALUE;

    public ThrottleSetting(PlatformEventType eventType) {
       this.eventType = Objects.requireNonNull(eventType);
    }

    @Override
    public String combine(Set<String> values) {
        Rate max = null;
        String text = null;
        for (String value : values) {
            Rate rate = Rate.of(value);
            if (rate != null) {
                if (max == null || rate.isHigher(max)) {
                    text = value;
                    max = rate;
                }
            }
        }
        // "off" is default
        return Objects.requireNonNullElse(text, DEFAULT_VALUE);
    }

    @Override
    public void setValue(String value) {
        if ("off".equals(value)) {
            eventType.setThrottle(-2, 1000);
            this.value = value;
            return;
        }

        Rate rate = Rate.of(value);
        if (rate != null) {
            long millis = 1000;
            long samples = rate.amount();
            TimespanUnit unit = rate.unit();
            // if unit is more than 1 s, set millis
            if (unit.nanos > SECONDS.nanos) {
                millis = unit.nanos / MILLISECONDS.nanos;
            }
            // if unit is less than 1 s, scale samples
            if (unit.nanos < SECONDS.nanos) {
                long perSecond = SECONDS.nanos / unit.nanos;
                samples *= Utils.multiplyOverflow(samples, perSecond, Long.MAX_VALUE);
            }
            eventType.setThrottle(samples, millis);
            this.value = value;
        }
    }

    @Override
    public String getValue() {
        return value;
    }
}


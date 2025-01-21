/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package jdk.jfr.internal.settings;

import java.util.Objects;
import java.util.Set;

import jdk.jfr.SettingControl;
import jdk.jfr.internal.PlatformEventType;

abstract class BooleanSetting extends SettingControl {
    private final PlatformEventType eventType;
    private final String defaultValue;
    private String value;

    public BooleanSetting(PlatformEventType eventType, String defaultValue) {
        this.eventType = Objects.requireNonNull(eventType);
        this.defaultValue = defaultValue;
        this.value = defaultValue;
        if (parse(defaultValue) == null) {
            throw new InternalError("Only 'true' or 'false' is allowed with class BooleanSetting");
        }
    }

    protected abstract void apply(PlatformEventType eventType, boolean value);

    @Override
    public String combine(Set<String> values) {
        if (values.contains("true")) {
            return "true";
        }
        if (values.contains("false")) {
            return "false";
        }
        return defaultValue;
    }

    @Override
    public void setValue(String value) {
        Boolean b = parse(value);
        if (b != null) {
            apply(eventType, b.booleanValue());
            this.value = value;
        }
    }

    @Override
    public String getValue() {
        return value;
    }

    private static Boolean parse(String value) {
        if ("true".equals(value)) {
            return Boolean.TRUE;
        }
        if ("false".equals(value)) {
            return Boolean.FALSE;
        }
        return null;
    }
}

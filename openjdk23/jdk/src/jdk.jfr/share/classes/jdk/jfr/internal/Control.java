/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.jfr.internal;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jdk.jfr.SettingControl;
import jdk.jfr.internal.settings.PeriodSetting;
import jdk.jfr.internal.settings.StackTraceSetting;
import jdk.jfr.internal.settings.ThresholdSetting;

final class Control {
    private static final int CACHE_SIZE = 5;
    private final Set<?>[] cachedUnions = new HashSet<?>[CACHE_SIZE];
    private final String[] cachedValues = new String[CACHE_SIZE];
    private final SettingControl delegate;
    private String defaultValue;
    private String lastValue;

    // called by exposed subclass in external API
    public Control(SettingControl delegate, String defaultValue) {
        this.delegate = delegate;
        this.defaultValue = defaultValue;
    }

    boolean isType(Class<? extends SettingControl> clazz) {
        return delegate.getClass() == clazz;
    }

    final void apply(Set<String> values) {
        setValue(findCombine(values));
    }

    final void setDefault() {
        if (defaultValue == null) {
            defaultValue = getValue();
        }
        apply(defaultValue);
    }

    public String getValue() {
        return delegate.getValue();
    }

    private void apply(String value) {
        if (lastValue != null && Objects.equals(value, lastValue)) {
            return;
        }
        setValue(value);
    }

    public void setValue(String value) {
        try {
            delegate.setValue(value);
            lastValue = delegate.getValue();
        } catch (Throwable t) {
            Logger.log(LogTag.JFR_SETTING, LogLevel.WARN, "Exception occurred when setting value \"" + value + "\". " + t.getMessage());
            lastValue = null;
        }
    }

    public String combine(Set<String> values) {
        return delegate.combine(values);
    }

    private final String findCombine(Set<String> values) {
        if (values.size() == 1) {
            return values.iterator().next();
        }
        for (int i = 0; i < CACHE_SIZE; i++) {
            if (Objects.equals(cachedUnions[i], values)) {
                return cachedValues[i];
            }
        }
        String result = combine(values);
        for (int i = 0; i < CACHE_SIZE - 1; i++) {
            cachedUnions[i + 1] = cachedUnions[i];
            cachedValues[i + 1] = cachedValues[i];
        }
        cachedValues[0] = result;
        cachedUnions[0] = values;
        return result;
    }

    final String getDefaultValue() {
        return defaultValue;
    }

    final String getLastValue() {
        return lastValue;
    }

    final SettingControl getSettingControl() {
        return delegate;
    }

    boolean isVisible(boolean hasEventHook) {
        if (isType(ThresholdSetting.class)) {
            return !hasEventHook;
        }
        if (isType(PeriodSetting.class)) {
            return hasEventHook;
        }
        if (isType(StackTraceSetting.class)) {
            return !hasEventHook;
        }
        return true;
    }
}

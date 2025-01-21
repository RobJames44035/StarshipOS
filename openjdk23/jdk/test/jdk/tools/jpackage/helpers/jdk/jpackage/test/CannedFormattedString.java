/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package jdk.jpackage.test;

import java.util.List;
import java.util.function.BiFunction;

public final class CannedFormattedString {

    CannedFormattedString(BiFunction<String, Object[], String> formatter,
            String key, Object[] args) {
        this.formatter = formatter;
        this.key = key;
        this.args = args;
    }

    public String getValue() {
        return formatter.apply(key, args);
    }

    @Override
    public String toString() {
        if (args.length == 0) {
            return String.format("%s", key);
        } else {
            return String.format("%s+%s", key, List.of(args));
        }
    }

    private final BiFunction<String, Object[], String> formatter;
    private final String key;
    private final Object[] args;
}

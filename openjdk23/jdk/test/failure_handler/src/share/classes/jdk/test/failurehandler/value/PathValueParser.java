/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

package jdk.test.failurehandler.value;

import java.io.File;

public class PathValueParser implements ValueParser {
    @Override
    public Object parse(Class<?> type, String value, String delimiter) {
        if (type.isArray()) {
            return new ArrayParser(this).parse(type, value, delimiter);
        }
        return new File(value).toPath();
    }
}

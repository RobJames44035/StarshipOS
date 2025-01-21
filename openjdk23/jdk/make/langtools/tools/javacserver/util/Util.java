/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

package javacserver.util;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Util {
    /**
     * Return a stream of strings, where the input string is split at line separators.
     */
    public static Stream<String> getLines(String str) {
        return str.isEmpty()
                ? Stream.empty()
                : Stream.of(str.split(Pattern.quote(System.lineSeparator())));
    }
}

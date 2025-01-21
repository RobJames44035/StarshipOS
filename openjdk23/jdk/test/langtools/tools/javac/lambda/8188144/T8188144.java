/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8188144
 * @summary regression in method reference type-checking
 */

import java.util.function.BiFunction;

public class T8188144 {
    public static void main(String[] args) {
        BiFunction<String, String, String> format = String::format;
        if (!format.apply("foo %s", "bar").endsWith("foo bar")) {
            throw new AssertionError("Unexpected output!");
        }
    }
}

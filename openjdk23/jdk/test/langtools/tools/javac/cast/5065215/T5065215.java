/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     5065215
 * @summary javac reports unnecessary unchecked warning
 * @compile -Xlint:unchecked -Werror T5065215.java
 */

public class T5065215 {
    static <T, U extends T> T[] cast(U[] a) { return (T[]) a; }
}

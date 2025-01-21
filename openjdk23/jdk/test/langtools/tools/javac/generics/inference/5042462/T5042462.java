/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/*
 * @test
 * @bug     5042462
 * @summary Problem with least upper bound (lub) and type variables
 * @compile T5042462.java
 */

public class T5042462 {
    <T, U extends T, V extends T> T cond1(boolean z, U x1, V x2) {
        T t = z? x1: x2;
        return z? x1: x2;
    }
}

/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug     6507024
 * @summary unchecked conversion between arrays fails after capture conversion
 * @author Maurizio Cimadamore
 *
 * @compile T6507024.java
 */

public class T6507024<T> {
    <Z> void m(T6507024<Z>[] results) {
        T6507024<Z>[] r = results.getClass().cast(null);
    }
}

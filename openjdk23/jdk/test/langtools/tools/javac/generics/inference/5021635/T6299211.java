/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6299211
 * @summary method type variable: inference broken for null
 * @compile T6299211.java
 */

public class T6299211 {
    void m() {
        java.util.Collections.max(null);
    }
}

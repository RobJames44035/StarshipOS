/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4242396 4254264
 * @summary Verify that a compatible interface can be cast to an array type.
 * @author maddox
 *
 * @run compile CastInterface2Array.java
 */

public class CastInterface2Array {

    java.io.Serializable s;

    int[] k;

    void foo() {
        k = (int[])s;
    }

    // A similar case to verify that
    // array classes implement java.io.Serializable

    byte[] array = null;
    java.io.Serializable c = array;
}

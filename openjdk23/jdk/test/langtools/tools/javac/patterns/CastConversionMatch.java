/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class CastConversionMatch {
    public static void meth() {
        Object o = 42;
        if (o instanceof int s) {
            System.out.println("Okay");
        } else {
            throw new AssertionError("broken");
        }
        System.out.println(">Test complete");
    }
}

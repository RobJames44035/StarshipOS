/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/**
 * @test
 * @bug 8024696 8075752
 * @summary Missing null check in bound method reference capture
 */

import java.util.function.Supplier;

public class MethodReferenceNullCheckTest {
    public static void main(String[] args) {
        String s = null;
        boolean npeFired = false;
        try {
            Supplier<Boolean> ss = s::isEmpty;
        } catch (NullPointerException npe) {
            npeFired = true;
        } finally {
            if (!npeFired)
                throw new AssertionError("NPE should have been thrown");
        }
    }
}

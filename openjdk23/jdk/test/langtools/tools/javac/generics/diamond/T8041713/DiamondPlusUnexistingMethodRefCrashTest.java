/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public class DiamondPlusUnexistingMethodRefCrashTest<T> {
    DiamondPlusUnexistingMethodRefCrashTest<String> m =
        new DiamondPlusUnexistingMethodRefCrashTest<>(DiamondPlusUnexistingMethodRefCrashTest::doNotExists);
}

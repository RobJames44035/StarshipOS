/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4253548
 * @summary Verify that compiler requires that static initializers may complete normally.
 * @author maddox
 *
 * @run compile/fail InitializerCompletion_3.java
 */

class InitializerCompletion_3 {
    static {
        throw new RuntimeException();
    }
}

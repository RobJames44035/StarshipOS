/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4253548
 * @summary Verify that compiler allows an instance initializer that may fail to complete normally
 * if it is possible that it might.
 * @author maddox
 *
 * @run compile InitializerCompletion_2.java
 */

class InitializerCompletion_2 {
    boolean stop = true;
    {
        if (stop)
            throw new RuntimeException();
    }
}

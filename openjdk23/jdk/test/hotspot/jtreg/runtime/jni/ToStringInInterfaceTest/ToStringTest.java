/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/* @test
 * @bug 8072588
 * @build InterfaceWithToString ImplementationOfWithToString
 * @run main/native ToStringTest
 */
public final class ToStringTest {

    static {
        System.loadLibrary("ToStringTest");
    }

    native static void nTest();

    public static void main(String[] args) throws Exception {
        nTest();
    }
}

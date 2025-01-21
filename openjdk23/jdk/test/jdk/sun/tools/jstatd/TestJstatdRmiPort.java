/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 *
 * @library /test/lib
 *
 * @build JstatdTest JstatGCUtilParser
 * @run main/timeout=60 TestJstatdRmiPort
 */
public class TestJstatdRmiPort {

    public static void main(String[] args) throws Throwable {
        testRmiPort();
        testRegistryAndRmiPorts();
    }

    private static void testRmiPort() throws Throwable {
        JstatdTest test = new JstatdTest();
        test.setUseDefaultRmiPort(false);
        test.doTest();
    }

    private static void testRegistryAndRmiPorts() throws Throwable {
        JstatdTest test = new JstatdTest();
        test.setUseDefaultPort(false);
        test.setUseDefaultRmiPort(false);
        test.doTest();
    }
}

/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 4990825 7092186
 * @key intermittent
 *
 * @library /test/lib
 *
 * @build JstatdTest JstatGCUtilParser
 * @run main/timeout=60 TestJstatdExternalRegistry
 */
public class TestJstatdExternalRegistry {

    public static void main(String[] args) throws Throwable {
        JstatdTest test = new JstatdTest();
        test.setUseDefaultPort(false);
        test.setWithExternalRegistry(true);
        test.doTest();
    }

}

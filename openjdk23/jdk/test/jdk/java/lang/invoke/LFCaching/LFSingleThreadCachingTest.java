/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test LFSingleThreadCachingTest
 * @bug 8046703
 * @key randomness
 * @summary Test verifies that lambda forms are cached when run with single thread
 * @author kshefov
 * @library /lib/testlibrary /java/lang/invoke/common /test/lib
 * @modules java.base/java.lang.ref:open
 *          java.base/java.lang.invoke:open
 *          java.management
 * @build jdk.test.lib.TimeLimitedRunner
 * @build TestMethods
 * @build LambdaFormTestCase
 * @build LFCachingTestCase
 * @build LFSingleThreadCachingTest
 * @run main/othervm -XX:ReservedCodeCacheSize=128m LFSingleThreadCachingTest
 */

import java.lang.invoke.MethodHandle;
import java.util.EnumSet;
import java.util.Map;

/**
 * Single threaded lambda forms caching test class.
 */
public final class LFSingleThreadCachingTest extends LFCachingTestCase {

    /**
     * Constructor for a single threaded lambda forms caching test case.
     *
     * @param testMethod A method from {@code j.l.i.MethodHandles} class that
     * returns a {@code j.l.i.MethodHandle} instance.
     */
    public LFSingleThreadCachingTest(TestMethods testMethod) {
        super(testMethod);
    }

    @Override
    public void doTest() {
        MethodHandle adapter1;
        MethodHandle adapter2;
        Map<String, Object> data = getTestMethod().getTestCaseData();
        try {
            adapter1 = getTestMethod().getTestCaseMH(data, TestMethods.Kind.ONE);
            adapter2 = getTestMethod().getTestCaseMH(data, TestMethods.Kind.TWO);
        } catch (NoSuchMethodException | IllegalAccessException ex) {
            throw new Error("Unexpected exception", ex);
        }
        checkLFCaching(adapter1, adapter2);
    }

    /**
     * Main routine for single threaded lambda forms caching test.
     *
     * @param args Accepts no arguments.
     */
    public static void main(String[] args) {
        LambdaFormTestCase.runTests(LFSingleThreadCachingTest::new,
                                    EnumSet.allOf(TestMethods.class));
    }
}

/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8238358
 * @summary Checking ACC_SYNTHETIC flag is generated for bridge method
 *          generated for lambda expressions and method references when
 *          compiling with --release 14.
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @library /tools/lib /tools/javac/lib ../lib
 * @build toolbox.ToolBox InMemoryFileManager
 *        ExpectedClass ExpectedClasses
 * @compile SyntheticTestDriver.java
 *          ../lib/TestResult.java ../lib/TestBase.java
 * @compile --source 14 -target 14 -XDdeduplicateLambdas=false BridgeMethodsForLambdaTargetRelease14Test.java
 * @run main SyntheticTestDriver BridgeMethodsForLambdaTargetRelease14Test
 */

import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * Synthetic members:
 * 1. inner class for Inner1.
 * 2. method for () -> {} in Inner1
 * 3. method for () -> {} in Inner2
 * 4. method references to private methods.
 * 5. method for super::function()
 * 6. method references to private static methods.
 * 7. access method for private method function().
 * 8. access method for private static method staticFunction().
 * 9. method reference to vararg method.
 * 10. method reference to array's method.
 * 11. constructors for Inner1 and Inner2.
 */
@ExpectedClass(className = "BridgeMethodsForLambdaTargetRelease14Test",
        expectedMethods = {"<init>()", "<clinit>()", "function(java.lang.Integer[])"},
        expectedNumberOfSyntheticMethods = 6)
@ExpectedClass(className = "BridgeMethodsForLambdaTargetRelease14Test$Inner1",
        expectedMethods = {"<init>(BridgeMethodsForLambdaTargetRelease14Test)", "function()", "run()"},
        expectedFields = "lambda1",
        expectedNumberOfSyntheticMethods = 1,
        expectedNumberOfSyntheticFields = 1)
@ExpectedClass(className = "BridgeMethodsForLambdaTargetRelease14Test$Inner2",
        expectedMethods = {"<init>()", "staticFunction()"},
        expectedFields = "lambda1",
        expectedNumberOfSyntheticMethods = 1)
@ExpectedClass(className = "BridgeMethodsForLambdaTargetRelease14Test$Inner3",
        expectedMethods = {"<init>(BridgeMethodsForLambdaTargetRelease14Test)", "function()"},
        expectedNumberOfSyntheticFields = 1)
@ExpectedClass(className = "BridgeMethodsForLambdaTargetRelease14Test$Inner4",
        expectedMethods = {"<init>(BridgeMethodsForLambdaTargetRelease14Test)", "function()"},
        expectedNumberOfSyntheticMethods = 1,
        expectedNumberOfSyntheticFields = 1)
public class BridgeMethodsForLambdaTargetRelease14Test {

    private class Inner1 implements Runnable {
        private Inner1() {
        }
        private Runnable lambda1 = () -> {
        };
        private void function() {
        }
        @Override
        public void run() {
        }
    }

    private static class Inner2 {
        private Runnable lambda1 = () -> {
        };
        private static void staticFunction() {
        }
    }

    private class Inner3 {
        public void function() {
        }
    }

    private class Inner4 extends Inner3 {
        @Override
        public void function() {
            Runnable r = super::function;
        }
    }

    private static int function(Integer...vararg) {
        return 0;
    }

    {
        Inner1 inner = new Inner1();
        Runnable l1 = inner::function;
        Runnable l2 = Inner1::new;
        inner.lambda1 = inner::function;
        Comparator<Integer> c = BridgeMethodsForLambdaTargetRelease14Test::function;
        IntStream.of(2).mapToObj(int[]::new);
    }

    static {
        Inner2 inner = new Inner2();
        Runnable l1 = Inner2::staticFunction;
    }
}

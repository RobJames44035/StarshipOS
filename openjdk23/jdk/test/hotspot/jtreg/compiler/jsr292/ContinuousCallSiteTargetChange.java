/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/**
 * @test
 * @library /test/lib /
 * @requires vm.flagless
 *
 * @build jdk.test.whitebox.WhiteBox
 * @run driver jdk.test.lib.helpers.ClassFileInstaller jdk.test.whitebox.WhiteBox
 * @run driver/timeout=180 compiler.jsr292.ContinuousCallSiteTargetChange
 */

package compiler.jsr292;

import jdk.test.lib.Asserts;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;
import jdk.test.whitebox.WhiteBox;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ContinuousCallSiteTargetChange {
    static final int ITERATIONS = Integer.parseInt(System.getProperty("iterations", "50"));

    static void runTest(Class<?> test, String... extraArgs) throws Exception {
        List<String> argsList = new ArrayList<>(
                List.of("-XX:+IgnoreUnrecognizedVMOptions",
                    "-XX:PerBytecodeRecompilationCutoff=10", "-XX:PerMethodRecompilationCutoff=10",
                    "-XX:+PrintCompilation", "-XX:+UnlockDiagnosticVMOptions", "-XX:+PrintInlining"));

        argsList.addAll(Arrays.asList(extraArgs));

        argsList.add(test.getName());
        argsList.add(Integer.toString(ITERATIONS));

        ProcessBuilder pb = ProcessTools.createLimitedTestJavaProcessBuilder(argsList);

        OutputAnalyzer analyzer = new OutputAnalyzer(pb.start());

        analyzer.shouldHaveExitValue(0);

        analyzer.shouldNotContain("made not compilable");
        analyzer.shouldNotContain("decompile_count > PerMethodRecompilationCutoff");

    }

    static void testServer(Class<?> test, String... args) throws Exception {
        List<String> extraArgsList = new ArrayList<>(
                List.of("-server", "-XX:-TieredCompilation", "-Xbootclasspath/a:.",
                        "-XX:+UnlockDiagnosticVMOptions", "-XX:+WhiteBoxAPI"));
        extraArgsList.addAll(Arrays.asList(args));

        runTest(test, extraArgsList.toArray(new String[extraArgsList.size()]));
    }

    static void testClient(Class<?> test, String... args) throws Exception {
        List<String> extraArgsList = new ArrayList<>(
                List.of("-client", "-XX:+TieredCompilation", "-XX:TieredStopAtLevel=1",
                        "-Xbootclasspath/a:.", "-XX:+UnlockDiagnosticVMOptions", "-XX:+WhiteBoxAPI"));
        extraArgsList.addAll(Arrays.asList(args));

        runTest(test, extraArgsList.toArray(new String[extraArgsList.size()]));
    }

    public static void main(String[] args) throws Exception {
        testServer(RecompilationTest.class, "-Xbatch");
        testClient(RecompilationTest.class, "-Xbatch");

        testServer(PingPongTest.class);
        testClient(PingPongTest.class);
    }

    static MethodHandle findStatic(Class<?> cls, String name, MethodType mt) {
        try {
            return MethodHandles.lookup().findStatic(cls, name, mt);
        } catch (Exception e) {
            throw new Error(e);
        }
    }

    static class RecompilationTest {
        static final MethodType mt = MethodType.methodType(void.class);
        static final CallSite cs = new MutableCallSite(mt);

        static final MethodHandle mh = cs.dynamicInvoker();

        static void f() {
        }

        static void test1() throws Throwable {
            mh.invokeExact();
        }

        static void test2() throws Throwable {
            cs.getTarget().invokeExact();
        }

        static void iteration() throws Throwable {
            MethodHandle mh1 = findStatic(RecompilationTest.class, "f", mt);
            cs.setTarget(mh1);
            for (int i = 0; i < 20_000; i++) {
                test1();
                test2();
            }
        }

        public static void main(String[] args) throws Throwable {
            int iterations = Integer.parseInt(args[0]);
            for (int i = 0; i < iterations; i++) {
                iteration();
            }
        }
    }

    static class PingPongTest {
        static final MethodType mt = MethodType.methodType(void.class);
        static final CallSite cs = new MutableCallSite(mt);

        static final MethodHandle mh = cs.dynamicInvoker();

        static final MethodHandle ping = findStatic(PingPongTest.class, "ping", mt);
        static final MethodHandle pong = findStatic(PingPongTest.class, "pong", mt);

        static void ping() {
            Asserts.assertEQ(cs.getTarget(), ping, "wrong call site target");
            cs.setTarget(pong);
        }

        static void pong() {
            Asserts.assertEQ(cs.getTarget(), pong, "wrong call site target");
            cs.setTarget(ping);
        }

        static void iteration() throws Throwable {
            cs.setTarget(ping);
            for (int i = 0; i < 20_000; i++) {
                mh.invokeExact();
            }
        }

        public static void main(String[] args) throws Throwable {
            int iterations = Integer.parseInt(args[0]);
            WhiteBox whiteBox = WhiteBox.getWhiteBox();
            for (int i = 0; i < iterations; i++) {
                iteration();
                whiteBox.fullGC();
            }
        }
    }
}

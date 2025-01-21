/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import jdk.test.lib.thread.VThreadPinner;

/*
 * @test
 * @summary javaagent + tracePinnedThreads will cause jvm crash/ run into deadlock when the virtual thread is pinned
 * @library /test/lib
 * @requires vm.continuations
 * @requires vm.jvmti
 * @modules java.base/java.lang:+open
 * @compile TestPinCaseWithCFLH.java
 * @build jdk.test.lib.Utils
 * @run driver jdk.test.lib.util.JavaAgentBuilder
 *             TestPinCaseWithCFLH TestPinCaseWithCFLH.jar
 * @run main/othervm/timeout=100  -Djdk.virtualThreadScheduler.maxPoolSize=1
 *       -Djdk.tracePinnedThreads=full --enable-native-access=ALL-UNNAMED
 *       -javaagent:TestPinCaseWithCFLH.jar TestPinCaseWithCFLH
 */
public class TestPinCaseWithCFLH {

    public static class TestClassFileTransformer implements ClassFileTransformer {
        public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
                                ProtectionDomain protectionDomain, byte[] classfileBuffer)
                                throws IllegalClassFormatException {
            return classfileBuffer;
        }
    }

    // Called when agent is loaded at startup
    public static void premain(String agentArgs, Instrumentation instrumentation) throws Exception {
        instrumentation.addTransformer(new TestClassFileTransformer());
    }

    private static int result = 0;

    public static void main(String[] args) throws Exception{
        Thread t1 = Thread.ofVirtual().name("vthread-1").start(() -> {
            VThreadPinner.runPinned(() -> {
                try {
                    // try yield, will pin,
                    // javaagent + tracePinnedThreads should not lead to crash
                    // (because of the class `PinnedThreadPrinter`)
                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        });
        t1.join();
    }

}
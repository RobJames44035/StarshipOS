/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
import java.io.PrintStream;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import jdk.internal.access.JavaAWTAccess;
import jdk.internal.access.SharedSecrets;

/*
 * @test
 * @bug 8025512
 *
 * @summary NPE with logging while launching webstart
 *
 * @modules java.base/jdk.internal.access
 *          java.logging
 * @build TestGetLoggerNPE
 * @run main/othervm TestGetLoggerNPE getLogger
 * @run main/othervm TestGetLoggerNPE getLogManager
 */
public class TestGetLoggerNPE {
    static volatile Throwable thrown = null;
    public static void main(String[] args) throws Exception {
        final String testCase = args.length == 0 ? "getLogger" : args[0];
        final JavaAWTAccessStub access = new JavaAWTAccessStub();
        SharedSecrets.setJavaAWTAccess(access);
        final ThreadGroup tg = new ThreadGroup("TestGroup");
        Thread t = new Thread(tg, "test") {
            public void run() {
                try {
                    access.setContext(Context.ONE);
                    final PrintStream out = System.out;
                    System.setOut(null);
                    try {
                        if ("getLogger".equals(testCase)) {
                           Logger.getLogger("sun.plugin");
                        } else {
                           LogManager.getLogManager();
                        }
                    } finally {
                        System.setOut(out);
                    }

                    System.out.println(Logger.global);
                } catch (Throwable x) {
                    x.printStackTrace();
                    thrown = x;
                }
            }
        };
        t.start();
        t.join();
        if (thrown == null) {
            System.out.println("PASSED: " + testCase);
        } else {
            System.err.println("FAILED: " + testCase);
            throw new Error("Test failed: " + testCase + " - " + thrown, thrown);
        }

    }

    static enum Context { ONE, TWO };

    static final class JavaAWTAccessStub implements JavaAWTAccess {
        private static final InheritableThreadLocal<Context> context = new InheritableThreadLocal<>();


        public void setContext(Context context) {
            JavaAWTAccessStub.context.set(context);
        }

        @Override
        public Object getAppletContext() {
            return context.get();
        }

     }

}

/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8046246
 * @summary Tests and benchmarks the JVMTI RedefineClasses when a
 *          single class (and its parent) contains many methods.
 *
 * @modules jdk.compiler
 *          java.instrument
 *          jdk.zipfs
 * @run build ManyMethodsBenchmarkApp ManyMethodsBenchmarkAgent
 * @run shell MakeJAR3.sh ManyMethodsBenchmarkAgent 'Can-Retransform-Classes: true'
 * @run main/othervm -javaagent:ManyMethodsBenchmarkAgent.jar ManyMethodsBenchmarkApp
 */
import java.lang.instrument.*;

public class ManyMethodsBenchmarkAgent
{
    public  static boolean fail = false;
    public  static boolean completed = false;
    private static  Instrumentation instrumentation;

    public static void
    premain(    String agentArgs,
                Instrumentation instrumentation) {
        System.out.println("ManyMethodsBenchmarkAgent started");
        ManyMethodsBenchmarkAgent.instrumentation = instrumentation;
        System.out.println("ManyMethodsBenchmarkAgent finished");
    }

    static void instr() {
        System.out.println("ManyMethodsBenchmarkAgent.instr started");

        Class[] allClasses = instrumentation.getAllLoadedClasses();

        for (int i = 0; i < allClasses.length; i++) {
            Class klass = allClasses[i];
            String name = klass.getName();
            if (!name.equals("Base")) {
                continue;
            }
            System.err.println("Instrumenting the class: " + klass);

            try {
                instrumentation.retransformClasses(klass);
            } catch (Throwable e) {
                System.err.println("Error: bad return from retransform: " + klass);
                System.err.println("  ERROR: " + e);
                fail = true;
            }
        }
        completed = true;
        System.out.println("ManyMethodsBenchmarkAgent.instr finished");
    }

}

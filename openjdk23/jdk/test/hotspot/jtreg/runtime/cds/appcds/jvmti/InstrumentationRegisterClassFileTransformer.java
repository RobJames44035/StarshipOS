/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

// This class is available on the classpath so it can be accessed by InstrumentationApp
public class InstrumentationRegisterClassFileTransformer {
    private static Instrumentation savedInstrumentation;

    public static void premain(String agentArguments, Instrumentation instrumentation) {
        System.out.println("InstrumentationRegisterClassFileTransformer.premain() is called");
        instrumentation.addTransformer(new InstrumentationClassFileTransformer(), /*canRetransform=*/true);
        savedInstrumentation = instrumentation;
    }

    public static Instrumentation getInstrumentation() {
        return savedInstrumentation;
    }

    public static void agentmain(String args, Instrumentation inst) throws Exception {
        premain(args, inst);
    }
}

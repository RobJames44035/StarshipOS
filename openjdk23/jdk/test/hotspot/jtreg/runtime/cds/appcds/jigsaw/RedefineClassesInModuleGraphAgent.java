/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

import java.lang.instrument.Instrumentation;

public class RedefineClassesInModuleGraphAgent {
    private static Instrumentation savedInstrumentation;

    public static void premain(String agentArguments, Instrumentation instrumentation) {
        instrumentation.addTransformer(new RedefineClassesInModuleGraphTransformer(), /*canRetransform=*/true);
        savedInstrumentation = instrumentation;
    }

    public static Instrumentation getInstrumentation() {
        return savedInstrumentation;
    }
}

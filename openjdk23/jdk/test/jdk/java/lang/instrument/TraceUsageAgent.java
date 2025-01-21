/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.util.Map;
import java.util.Set;

/**
 * Agent used by TraceUsageTest. The premain and agentmain methods invoke Instrumentation
 * methods so the usages can be traced by the test.
 */
public class TraceUsageAgent {
    public static void premain(String methodNames, Instrumentation inst) throws Exception {
        test(methodNames, inst);
    }

    public static void agentmain(String methodNames, Instrumentation inst) throws Exception {
        test(methodNames, inst);
    }

    private static void test(String methodNames, Instrumentation inst) throws Exception {
        for (String methodName : methodNames.split(",")) {
            switch (methodName) {
                case "addTransformer" -> {
                    var transformer = new ClassFileTransformer() { };
                    inst.addTransformer(transformer);
                }
                case "retransformClasses" -> {
                    inst.retransformClasses(Object.class);
                }
                case "redefineModule" -> {
                    Module base = Object.class.getModule();
                    inst.redefineModule(base, Set.of(), Map.of(), Map.of(), Set.of(), Map.of());
                }
                default -> {
                    throw new RuntimeException("Unknown method name: " + methodName);
                }
            }
        }
    }
}

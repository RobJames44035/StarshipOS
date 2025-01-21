/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.HashMap;

// This is a test utility class used to transform
// specified classes via initial transformation (ClassFileLoadHook).
// Names of classes to be transformed are supplied as arguments,
// the phrase to be transformed is a hard-coded predefined
// fairly unique phrase.

public class TransformerAgent {
    private static String[] classesToTransform;


    private static void log(String msg) {
        System.out.println("TransformerAgent: " + msg);
    }


    // arguments are comma-separated list of classes to transform
    public static void premain(String agentArguments, Instrumentation instrumentation) {
        log("premain() is called, arguments = " + agentArguments);
        classesToTransform = agentArguments.split(",");
        instrumentation.addTransformer(new SimpleTransformer(), /*canRetransform=*/true);
    }


    public static void agentmain(String args, Instrumentation inst) throws Exception {
        log("agentmain() is called");
        premain(args, inst);
    }


    static class SimpleTransformer implements ClassFileTransformer {
        public byte[] transform(ClassLoader loader, String name, Class<?> classBeingRedefined,
                                ProtectionDomain pd, byte[] buffer) throws IllegalClassFormatException {
            try {
                // Printing cause ClassCircularityError for java/util/concurrent/locks/AbstractQueuedSynchronizer$ExclusiveNode
                // So don't try to print some system classes
                if (!name.startsWith("java/")) {
                    log("SimpleTransformer called for: " + name + "@" + incrCounter(name));
                }
                if (!shouldTransform(name))
                    return null;

                log("transforming: class name = " + name);
                int nrOfReplacements = TransformUtil.replace(buffer, TransformUtil.BeforePattern,
                                                           TransformUtil.AfterPattern);
                log("replaced the string, nrOfReplacements = " + nrOfReplacements);
            } catch (Throwable t) {
                // The retransform native code that called this method does not propagate
                // exceptions. Instead of getting an uninformative generic error, catch
                // problems here and print it, then exit.
                log("Transformation failed!");
                t.printStackTrace();
                System.exit(1);
            }
            return buffer;
        }

        // Check class name pattern, since test should only transform certain classes
        private static boolean shouldTransform(String name) {
            for (String match : classesToTransform) {
                if (name.matches(match)) {
                    log("shouldTransform: match-found, match = " + match);
                    return true;
                }
            }

            return false;
        }
    }


    static HashMap<String, Integer> counterMap = new HashMap<>();

    static Integer incrCounter(String className) {
        Integer i = counterMap.get(className);
        if (i == null) {
            i = Integer.valueOf(1);
        } else {
            i = Integer.valueOf(i.intValue() + 1);
        }
        counterMap.put(className, i);
        return i;
    }
}

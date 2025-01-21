/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */
import java.security.*;
import java.lang.instrument.*;
import java.lang.reflect.*;

public class Agent implements ClassFileTransformer {
    public synchronized byte[] transform(final ClassLoader classLoader,
                                         final String className,
                                         Class<?> classBeingRedefined,
                                         ProtectionDomain protectionDomain,
                                         byte[] classfileBuffer) {
        System.out.println("Transforming class " + className);
        return classfileBuffer;
    }

    public static void redefine(String agentArgs, Instrumentation instrumentation, Class to_redefine) {

        try {
            instrumentation.retransformClasses(to_redefine);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void premain(String agentArgs, Instrumentation instrumentation) {
        Agent transformer = new Agent();
        instrumentation.addTransformer(transformer, true);

        // Redefine java/lang/Object and java/lang/reflect/Method.invoke and
        // java/lang/ClassLoader
        Class object_class = Object.class;
        redefine(agentArgs, instrumentation, object_class);

        Class method_class = Method.class;
        redefine(agentArgs, instrumentation, method_class);

        Class loader_class = ClassLoader.class;
        redefine(agentArgs, instrumentation, loader_class);

        instrumentation.removeTransformer(transformer);
    }

    public static void main(String[] args) {
        byte[] ba = new byte[0];

        // If it survives 100 GC's, it's good.
        for (int i = 0; i < 100 ; i++) {
            System.gc();
            ba.clone();
        }
        try {
            // Use java/lang/reflect/Method.invoke to call
            WalkThroughInvoke a = new WalkThroughInvoke();
            Class aclass = WalkThroughInvoke.class;
            Method m = aclass.getMethod("stackWalk");
            m.invoke(a);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
}

/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;
import java.util.Arrays;

public class SimpleAgent {
    public static void premain(String agentArg, Instrumentation instrumentation) throws Exception {
        System.out.println("inside SimpleAgent");
        if (agentArg == null) return;
        if (agentArg.equals("OldSuper")) {
            // Only load the class if the test requires it.
            Class<?> cls = Class.forName("OldSuper", true, ClassLoader.getSystemClassLoader());
        } else if (agentArg.equals("doTransform")) {
            ClassFileTransformer transformer = new ClassFileTransformer() {
            @Override
            public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) {
                 System.out.printf("%n Transforming %s", className);
                 return Arrays.copyOf(classfileBuffer, classfileBuffer.length);
               }
             };
             instrumentation.addTransformer(transformer);
        }
    }
}

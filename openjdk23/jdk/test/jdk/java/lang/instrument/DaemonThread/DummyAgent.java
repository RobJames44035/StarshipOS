/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

public class DummyAgent implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className,
                            Class<?> classBeingRedefined, ProtectionDomain protectionDomain,
                            byte[] classfileBuffer) throws IllegalClassFormatException {

        /* The Daemon Thread bug is timing dependent and you want the transform method
         * to return ASAP - so just return the buffer will be fine
         */
        return classfileBuffer;
    }

    public static void premain(String agentArgs, Instrumentation inst) {
        inst.addTransformer(new DummyAgent(), false);
    }

}

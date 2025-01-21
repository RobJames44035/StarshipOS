/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */


import java.io.File;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.ProtectionDomain;

public class ParallelTransformerLoaderAgent {
    private static URL sURL;
    private static ClassLoader sClassLoader;

    public static synchronized ClassLoader getClassLoader() {
        return sClassLoader;
    }

    public static synchronized void generateNewClassLoader() {
        sClassLoader = new URLClassLoader(new URL[]{sURL});
    }

    public static void premain(String agentArgs, Instrumentation instrumentation) throws Exception {
        if (agentArgs == null || agentArgs == "") {
            System.err.println("Error: No jar file name provided, test will not run.");
            return;
        }

        sURL = (new File(agentArgs)).toURI().toURL();
        System.out.println("Using jar file: " + sURL);
        generateNewClassLoader();

        instrumentation.addTransformer(new TestTransformer());
    }

    private static class TestTransformer implements ClassFileTransformer {
        public byte[] transform(ClassLoader loader,
                                String className,
                                Class classBeingRedefined,
                                ProtectionDomain protectionDomain,
                                byte[] classfileBuffer)
                throws IllegalClassFormatException {
            String tName = Thread.currentThread().getName();

            // Load additional classes when called from thread 'TestThread'
            // When a class is loaded during a callback handling the boot loader, we can
            // run into ClassCircularityError if the ClassFileLoadHook is set early enough
            // to catch classes needed during class loading, e.g.
            //          sun.misc.URLClassPath$JarLoader$2.
            // The goal of the test is to stress class loading on the test class loaders.

            if (tName.equals("TestThread") && loader != null) {
                loadClasses(3);
            }
            return null;
        }

        public static void loadClasses(int index) {
            ClassLoader loader = ParallelTransformerLoaderAgent.getClassLoader();
            try {
                Class.forName("TestClass" + index, true, loader);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

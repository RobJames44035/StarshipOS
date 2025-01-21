/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * @test
 * @bug 8008678
 * @summary JSR 292: constant pool reconstitution must support pseudo strings
 * @requires vm.jvmti
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 *          java.instrument
 *          java.management
 *          jdk.jartool/sun.tools.jar
 * @compile -XDignore.symbol.file TestLambdaFormRetransformation.java
 * @run driver TestLambdaFormRetransformation
 */

import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.ProtectionDomain;
import java.util.Arrays;

import jdk.test.lib.process.ExitCode;
import jdk.test.lib.process.OutputAnalyzer;
import jdk.test.lib.process.ProcessTools;

public class TestLambdaFormRetransformation {
    private static String MANIFEST = String.format("Manifest-Version: 1.0\n" +
                                                   "Premain-Class: %s\n" +
                                                   "Can-Retransform-Classes: true\n",
                                                   Agent.class.getName());

    private static String CP = System.getProperty("test.classes");

    public static void main(String args[]) throws Throwable {
        Path agent = TestLambdaFormRetransformation.buildAgent();
        OutputAnalyzer oa = ProcessTools.executeTestJava("-javaagent:" +
                                agent.toAbsolutePath().toString(), "-version");
        oa.shouldHaveExitValue(ExitCode.OK.value);
    }

    private static Path buildAgent() throws IOException {
        Path manifest = TestLambdaFormRetransformation.createManifest();
        Path jar = Files.createTempFile(Paths.get("."), null, ".jar");

        String[] args = new String[] {
            "-cfm",
            jar.toAbsolutePath().toString(),
            manifest.toAbsolutePath().toString(),
            "-C",
            TestLambdaFormRetransformation.CP,
            Agent.class.getName() + ".class"
        };

        sun.tools.jar.Main jarTool = new sun.tools.jar.Main(System.out, System.err, "jar");

        if (!jarTool.run(args)) {
            throw new Error("jar failed: args=" + Arrays.toString(args));
        }
        return jar;
    }

    private static Path createManifest() throws IOException {
        Path manifest = Files.createTempFile(Paths.get("."), null, ".mf");
        byte[] manifestBytes = TestLambdaFormRetransformation.MANIFEST.getBytes();
        Files.write(manifest, manifestBytes);
        return manifest;
    }
}

class Agent implements ClassFileTransformer {
    private static Runnable lambda = () -> {
        System.out.println("I'll crash you!");
    };

    public static void premain(String args, Instrumentation instrumentation) {
        if (!instrumentation.isRetransformClassesSupported()) {
            System.out.println("Class retransformation is not supported.");
            return;
        }
        System.out.println("Calling lambda to ensure that lambda forms were created");

        Agent.lambda.run();

        System.out.println("Registering class file transformer");

        instrumentation.addTransformer(new Agent());

        for (Class c : instrumentation.getAllLoadedClasses()) {
            if (c.getName().contains("LambdaForm") &&
                instrumentation.isModifiableClass(c)) {
                System.out.format("We've found a modifiable lambda form: %s%n", c.getName());
                try {
                    instrumentation.retransformClasses(c);
                } catch (UnmodifiableClassException e) {
                    throw new AssertionError("Modification of modifiable class " +
                                             "caused UnmodifiableClassException", e);
                }
            }
        }
    }

    public static void main(String args[]) {
    }

    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer
                           ) throws IllegalClassFormatException {
        System.out.println("Transforming " + className);
        return classfileBuffer.clone();
    }
}

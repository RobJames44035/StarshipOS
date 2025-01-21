/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.test.lib.jittester;

import java.io.IOException;
import java.util.function.Function;
import jdk.test.lib.jittester.visitors.JavaCodeVisitor;

/**
 * Generates java source code from IRTree
 */
public class JavaCodeGenerator extends TestsGenerator {
    private static final String DEFAULT_SUFFIX = "java_tests";

    JavaCodeGenerator() {
        this(DEFAULT_SUFFIX, JavaCodeGenerator::generatePrerunAction, "-Xcomp");
    }

    JavaCodeGenerator(String prefix, Function<String, String[]> preRunActions, String jtDriverOptions) {
        super(prefix, preRunActions, jtDriverOptions);
    }

    @Override
    public void accept(IRTreeGenerator.Test test) {
        IRNode mainClass = test.mainClass();
        String mainClassName = mainClass.getName();
        generateSources(test.seed(), mainClass, test.privateClasses());
        compilePrinter();
        compileJavaFile(mainClassName);
        generateGoldenOut(mainClassName);
    }

    private void generateSources(long seed, IRNode mainClass, IRNode privateClasses) {
        String mainClassName = mainClass.getName();
        StringBuilder code = new StringBuilder();
        JavaCodeVisitor vis = new JavaCodeVisitor();
        code.append(getJtregHeader(mainClassName, seed));
        if (privateClasses != null) {
            code.append(privateClasses.accept(vis));
        }
        code.append(mainClass.accept(vis));
        ensureExisting(generatorDir);
        writeFile(generatorDir, mainClassName + ".java", code.toString());
    }

    private void compileJavaFile(String mainClassName) {
        String classPath = tmpDir.path.toString();
        ProcessBuilder pb = new ProcessBuilder(JAVAC,
                "-d", classPath,
                "-cp", classPath,
                generatorDir.resolve(mainClassName + ".java").toString());
        try {
            int r = runProcess(pb, tmpDir.path.resolve(mainClassName + ".javac").toString());
            if (r != 0) {
                throw new Error("Can't compile sources, exit code = " + r);
            }
        } catch (IOException | InterruptedException e) {
            throw new Error("Can't compile sources ", e);
        }
    }

    protected static String[] generatePrerunAction(String mainClassName) {
        return new String[] {"@compile " + mainClassName + ".java"};
    }

    public static void main(String[] args) throws Exception {
        ProductionParams.initializeFromCmdline(args);
        IRTreeGenerator.initializeWithProductionParams();

        JavaCodeGenerator generator = new JavaCodeGenerator();

        for (String mainClass : ProductionParams.mainClassNames.value()) {
            var test = IRTreeGenerator.generateIRTree(mainClass);
            generator.generateSources(test.seed(), test.mainClass(), test.privateClasses());
        }
    }
}

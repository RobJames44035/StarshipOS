/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package jdk.test.lib.jittester;

import jdk.test.lib.jittester.visitors.ByteCodeVisitor;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Function;

/**
 * Generates class files from IRTree
 */
class ByteCodeGenerator extends TestsGenerator {
    private static final String DEFAULT_SUFFIX = "bytecode_tests";

    ByteCodeGenerator() {
        super(DEFAULT_SUFFIX, s -> new String[0], "-Xcomp");
    }

    ByteCodeGenerator(String suffix, Function<String, String[]> preRunActions, String jtDriverOptions) {
        super(suffix, preRunActions, jtDriverOptions);
    }

    @Override
    public void accept(IRTreeGenerator.Test test) {
        IRNode mainClass = test.mainClass();
        generateClassFiles(mainClass, test.privateClasses());
        generateSeparateJtregHeader(test.seed(), mainClass);
        compilePrinter();
        generateGoldenOut(mainClass.getName());
    }

    private void generateSeparateJtregHeader(long seed, IRNode mainClass) {
        String mainClassName = mainClass.getName();
        writeFile(generatorDir, mainClassName + ".java", getJtregHeader(mainClassName, seed));
    }

    private void generateClassFiles(IRNode mainClass, IRNode privateClasses) {
        String mainClassName = mainClass.getName();
        ensureExisting(generatorDir);
        try {
            ByteCodeVisitor vis = new ByteCodeVisitor();
            if (privateClasses != null) {
                privateClasses.accept(vis);
            }
            mainClass.accept(vis);
            writeFile(mainClassName + ".class", vis.getByteCode(mainClassName));
            if (privateClasses != null) {
                privateClasses.getChildren().forEach(c -> {
                    String name = c.getName();
                    writeFile(name + ".class", vis.getByteCode(name));
                });
            }
        } catch (Throwable t) {
            Path errFile = generatorDir.resolve(mainClassName + ".err");
            try (PrintWriter pw = new PrintWriter(Files.newOutputStream(errFile,
                    StandardOpenOption.CREATE, StandardOpenOption.WRITE))) {
                t.printStackTrace(pw);
            } catch (IOException e) {
                t.printStackTrace();
                throw new Error("can't write error to error file " + errFile, e);
            }
        }
    }

    private void writeFile(String fileName, byte[] bytecode) {
        try {
            Files.write(generatorDir.resolve(fileName), bytecode);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        ProductionParams.initializeFromCmdline(args);
        IRTreeGenerator.initializeWithProductionParams();

        ByteCodeGenerator generator = new ByteCodeGenerator();

        for (String mainClass : ProductionParams.mainClassNames.value()) {
            var test = IRTreeGenerator.generateIRTree(mainClass);
            generator.generateClassFiles(test.mainClass(), test.privateClasses());
            generator.generateSeparateJtregHeader(test.seed(), test.mainClass());
        }
    }
}

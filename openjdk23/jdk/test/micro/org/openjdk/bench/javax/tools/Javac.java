/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */
package org.openjdk.bench.javax.tools;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

import javax.tools.JavaCompiler;
import javax.tools.SimpleJavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.StandardLocation;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@State(Scope.Thread)
public class Javac {
    private List<JavaSourceFromString> compilationUnits;
    private JavaCompiler compiler;
    private StandardJavaFileManager fileManager;
    private File classOutputDir;

    @Setup
    public void prepare() throws IOException {
        String helloWorld = "class Apan {   \n" + "  public static void main(String args[]) {\n"
                + "     System.out.println(\"hej apa\");\n" + "  }\n" + "}\n";

        compiler = ToolProvider.getSystemJavaCompiler();

        fileManager = compiler.getStandardFileManager(null, null, null);
        classOutputDir = Files.createTempDirectory(Javac.class.getName()).toFile();
        fileManager.setLocation(StandardLocation.CLASS_OUTPUT, Collections.singleton(classOutputDir));

        compilationUnits = new ArrayList<>();
        compilationUnits.add(new JavaSourceFromString("Apan", helloWorld));
    }

    @TearDown
    public void tearDown() {
        for (File f : classOutputDir.listFiles()) {
            if (f.isFile()) {
                f.delete();
            } else {
                throw new IllegalStateException("Unexpected non-file: " + f);
            }
        }
        classOutputDir.delete();
    }

    @Benchmark
    public Boolean testCompile() throws Exception {
        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, null, null, null, compilationUnits);
        return task.call();
    }

    private static class JavaSourceFromString extends SimpleJavaFileObject {
        final String code;

        JavaSourceFromString(String name, String code) {
            super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
                    Kind.SOURCE);
            this.code = code;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return code;
        }
    }
}

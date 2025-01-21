/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8263452
 * @summary Verify javac does not need a long time to process sources with deep class nesting
 *          and deep inheritance hierarchies.
 * @modules jdk.compiler
 */

import java.util.Arrays;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;

import com.sun.source.util.JavacTask;
import java.io.IOException;
import java.net.URI;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

public class SuperClassAndNesting {

    private static final int SIZE = 100;

    public static void main(String... args) throws IOException {
        new SuperClassAndNesting().run();
    }

    void run() throws IOException {
        compileTestClass(generateTestClass(SIZE));
    }

    String generateTestClass(int depth) {
        StringBuilder clazz = new StringBuilder();
        clazz.append("""
                     class Test {
                     class T0 extends java.util.ArrayList {
                     """);
        for (int i = 1; i < depth; i++) {
            clazz.append("class T" + i + " extends T" + (i - 1) + " {\n");
        }
        for (int i = 0; i < depth; i++) {
            clazz.append("}\n");
        }
        clazz.append("}\n");
        return clazz.toString();
    }

    void compileTestClass(String code) throws IOException {
        final JavaCompiler tool = ToolProvider.getSystemJavaCompiler();
        assert tool != null;

        JavacTask ct = (JavacTask) tool.getTask(null, null, null,
            null, null, Arrays.asList(new MyFileObject(code)));
        ct.analyze();
    }

    static class MyFileObject extends SimpleJavaFileObject {
        private final String text;

        public MyFileObject(String text) {
            super(URI.create("myfo:/Test.java"), JavaFileObject.Kind.SOURCE);
            this.text = text;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return text;
        }
    }
}
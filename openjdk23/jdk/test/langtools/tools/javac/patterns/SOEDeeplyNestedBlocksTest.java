/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8322992 8331030
 * @summary Javac fails with StackOverflowError when compiling deeply nested synchronized blocks
 * @run main SOEDeeplyNestedBlocksTest
 */

import java.net.*;
import java.util.*;
import javax.tools.*;

public class SOEDeeplyNestedBlocksTest {

    static final int NESTING_DEPTH = 1000;

    public static void main(String... args) {
        var lines = new ArrayList<String>();
        lines.add("class Test {");
        lines.add("  static { ");
        for (int i = 0; i < NESTING_DEPTH; i++) lines.add("    synchronized (Test.class) {");
        for (int i = 0; i < NESTING_DEPTH; i++) lines.add("    }");
        lines.add("  }");
        lines.add("}");

        var source = SimpleJavaFileObject.forSource(URI.create("mem://Test.java"), String.join("\n", lines));
        var compiler = ToolProvider.getSystemJavaCompiler();
        var task = compiler.getTask(null, null, noErrors, null, null, List.of(source));
        task.call();
    }

    static DiagnosticListener<? super JavaFileObject> noErrors = d -> {
        System.out.println(d);
        if (d.getKind() == Diagnostic.Kind.ERROR) {
            throw new AssertionError(d.getMessage(null));
        }
    };
}

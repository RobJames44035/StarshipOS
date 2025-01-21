/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     4164450
 * @summary JSR 199: Standard interface for Java compilers
 * @author  Peter von der Ah\u00e9
 * @modules java.compiler
 *          java.desktop
 *          jdk.compiler
 * @compile TestEvalExpression.java evalexpr/ByteArrayClassLoader.java  evalexpr/CompileFromString.java  evalexpr/MemoryFileManager.java
 * @run main TestEvalExpression
 */

import java.lang.reflect.Method;
import java.util.*;
import javax.swing.JOptionPane;
import javax.tools.*;
import static evalexpr.CompileFromString.*;

public class TestEvalExpression {
    static int errorCount = 0;
    static class Listener implements DiagnosticListener<JavaFileObject> {
        public void report(Diagnostic<? extends JavaFileObject> message) {
            if (message.getKind() == Diagnostic.Kind.ERROR)
                errorCount++;
            System.err.println(message);
        }
    }

    public static void main(String[] args) {
        // Get a compiler tool
        final JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        final List<String> compilerFlags = new ArrayList();
        compilerFlags.add("-Xlint:all"); // report all warnings
        compilerFlags.add("-g:none"); // don't generate debug info
        final DiagnosticListener<JavaFileObject> listener = new Listener();
        String expression = "System.getProperty(\"java.vendor\")";
        Object result = null;
        try {
            result = evalExpression(compiler, listener, compilerFlags, expression);
        } catch (Exception e) {
            throw new AssertionError(e);
        }
        if (result == ERROR)
            throw new AssertionError(result);
        System.out.format("%s => %s%n", expression, result);
        if (!System.getProperty("java.vendor").equals(result))
            throw new AssertionError(result);
        if (errorCount != 0)
            throw new AssertionError(errorCount);
        try {
            result = evalExpression(compiler, listener, compilerFlags, "fisk hest");
        } catch (Exception e) {
            throw new AssertionError(e);
        }
        if (errorCount == 0)
            throw new AssertionError(errorCount);
    }
}

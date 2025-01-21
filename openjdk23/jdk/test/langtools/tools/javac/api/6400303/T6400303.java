/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug     6400303
 * @summary REGRESSION: javadoc crashes in b75
 * @author  Peter von der Ah\u00e9
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.code
 *          jdk.compiler/com.sun.tools.javac.comp
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 * @compile Test1.java
 * @compile Test2.java
 * @run main/othervm -esa T6400303
 */

import javax.tools.ToolProvider;

import com.sun.tools.javac.api.JavacTaskImpl;
import com.sun.tools.javac.code.Symbol.CompletionFailure;
import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.util.Names;

public class T6400303 {
    public static void main(String... args) {
        javax.tools.JavaCompiler tool = ToolProvider.getSystemJavaCompiler();
        JavacTaskImpl task = (JavacTaskImpl)tool.getTask(null, null, null, null, null, null);
        Names names = Names.instance(task.getContext());
        Symtab syms = Symtab.instance(task.getContext());
        task.ensureEntered();
        try {
            syms.enterClass(syms.unnamedModule, names.fromString("Test$1")).complete();
        } catch (CompletionFailure ex) {
            System.err.println("Got expected completion failure: " + ex.getLocalizedMessage());
            return;
        }
        throw new AssertionError("No error reported");
    }
}

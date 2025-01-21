/*
 * StarshipOS Copyright (c) 2006-2025. R.A. James
 */

/**
 * @test
 * @bug     6374357 6308351
 * @summary PackageElement.getEnclosedElements() throws ClassReader$BadClassFileException
 * @author  Peter von der Ah\u00e9
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.code
 *          jdk.compiler/com.sun.tools.javac.comp
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 * @run main TestResolveIdent
 */

import com.sun.tools.javac.api.JavacTaskImpl;
import com.sun.tools.javac.code.Symtab;
import com.sun.tools.javac.main.JavaCompiler;
import java.io.IOException;
import javax.tools.ToolProvider;

public class TestResolveIdent {

    @SuppressWarnings("deprecation")
    static Class<?> getDeprecatedClass() {
        return java.io.StringBufferInputStream.class;
    }

    public static void main(String[] args) throws IOException {
        javax.tools.JavaCompiler tool = ToolProvider.getSystemJavaCompiler();
        JavacTaskImpl task = (JavacTaskImpl)tool.getTask(null, null, null, null, null, null);
        JavaCompiler compiler = JavaCompiler.instance(task.getContext());
        Symtab syms = Symtab.instance(task.getContext());
        task.ensureEntered();
        System.out.println(compiler.resolveIdent(syms.unnamedModule, getDeprecatedClass().getCanonicalName()));
    }

}

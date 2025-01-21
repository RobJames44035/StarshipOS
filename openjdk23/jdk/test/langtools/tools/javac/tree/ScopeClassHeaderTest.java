/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug     8186694
 * @summary Verify that taking a Scope inside a class header
 *          does not taint internal structures
 * @modules jdk.compiler
 * @run main ScopeClassHeaderTest
 */

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Scope;
import com.sun.source.util.JavacTask;
import com.sun.source.util.TreePathScanner;
import com.sun.source.util.Trees;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import com.sun.source.tree.IdentifierTree;

public class ScopeClassHeaderTest {

    public static void main(String... args) throws Exception {
        verifyScopeForClassHeader();
    }

    private static void verifyScopeForClassHeader() throws Exception {
        JavaCompiler tool = ToolProvider.getSystemJavaCompiler();
        JavaFileObject source = new SimpleJavaFileObject(URI.create("mem://Test.java"), Kind.SOURCE) {
            @Override public CharSequence getCharContent(boolean ignoreEncodingErrors) throws IOException {
                return "import java.util.*; class O { public void m() { class X<T extends ArrayList> { public void test() { String o; } } } }";
            }
            @Override public boolean isNameCompatible(String simpleName, Kind kind) {
                return !"module-info".equals(simpleName);
            }
        };
        Iterable<? extends JavaFileObject> fos = Collections.singletonList(source);
        JavacTask task = (JavacTask) tool.getTask(null, null, null, new ArrayList<String>(), null, fos);
        final Trees trees = Trees.instance(task);
        CompilationUnitTree cu = task.parse().iterator().next();

        task.analyze();

        new TreePathScanner<Void, Void>() {
            @Override
            public Void visitIdentifier(IdentifierTree node, Void p) {
                if (node.getName().contentEquals("ArrayList") || node.getName().contentEquals("String")) {
                    Scope scope = trees.getScope(getCurrentPath());
                    System.err.println("scope: " + scope);
                }
                return super.visitIdentifier(node, p);
            }
        }.scan(cu, null);
    }
}

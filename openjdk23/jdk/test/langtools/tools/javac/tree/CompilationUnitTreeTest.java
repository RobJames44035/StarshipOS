/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8255464
 * @summary Cannot access ModuleTree in a CompilationUnitTree
 * @modules jdk.compiler/com.sun.tools.javac.api
 */

import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;

public class CompilationUnitTreeTest {
    public static void main(String... args) throws Exception {
        new CompilationUnitTreeTest().run();
    }

    PrintStream out = System.err;
    int errors;

    void run() throws Exception {
        testModuleCompilationUnit();
        testOrdinaryCompilationUnit();
        if (errors > 0) {
            out.println(errors + " errors");
            throw new Exception(errors + " errors");
        }
    }

    void testModuleCompilationUnit() throws IOException {
        out.println("Test ModuleCompilationUnit");
        CompilationUnitTree cut = parse("import java.util.*; module m { }");
        checkTree("package", cut.getPackage(),     null);
        checkList("imports", cut.getImports(),      List.of("IMPORT import java.util.*;"));
        checkList("type decls", cut.getTypeDecls(), List.of());
        checkTree("module", cut.getModule(),       "MODULE module m { }");
    }

    void testOrdinaryCompilationUnit() throws IOException {
        out.println("Test OrdinaryCompilationUnit");
        CompilationUnitTree cut = parse("package p; import java.util.*; public class C { };");
        checkTree("package",    cut.getPackage(),  "PACKAGE package p;");
        checkList("imports",    cut.getImports(),   List.of("IMPORT import java.util.*;"));
        checkList("type decls", cut.getTypeDecls(), List.of("CLASS public class C { }", "EMPTY_STATEMENT ;"));
        checkTree("module",     cut.getModule(),   null);
    }

    void checkTree(String label, Tree tree, String expect) {
        String f = tree == null ? null
                : tree.getKind() + " " + tree.toString().trim().replaceAll("\\s+", " ");
        if (Objects.equals(f, expect)) {
            out.println("  " + label + " OK: " + expect);
        } else {
            out.println("  " + label + " error");
            out.println("    expect: " + expect);
            out.println("     found: " + f);
            errors++;
        }
    }

    void checkList(String label, List<? extends Tree> trees, List<String> expect) {
        Objects.requireNonNull(expect);
        if (trees == null) {
            out.println("  " + label + " error: list is null");
            errors++;
            return;
        }
        if (trees.size() != expect.size()) {
            out.println("  " + label + " error: list size mismatch");
            out.println("    expect: " + expect.size());
            out.println("     found: " + trees.size());
            errors++;
        }
        // compare entries in both lists
        for (int i = 0; i < Math.min(trees.size(), expect.size()); i++) {
            Tree ti = trees.get(i);
            String ei = expect.get(i);
            checkTree(label + "[" + i + "]", ti, ei);
        }
        // show any excess entries in expect list
        for (int i = trees.size(); i < expect.size(); i++) {
            String ei = expect.get(i);
            out.println("    " + label + "[" + i + "]: expect: " + ei);
        }
        // show any excess entries in trees list
        for (int i = expect.size(); i < trees.size(); i++) {
            Tree ti = trees.get(i);
            String fi = ti == null ? null : ti.getKind() + " " + ti.toString();
            out.println("    " + label + "[" + i + "]: found: " + fi);
        }
    }

    CompilationUnitTree parse(String text) throws IOException {
        JavacTool tool = JavacTool.create();
        JavacTask ct = tool.getTask(null, null, null,
                null, null, List.of(new MyFileObject(text)));
        return ct.parse().iterator().next();
    }

    static class MyFileObject extends SimpleJavaFileObject {

        private final String text;

        public MyFileObject(String text) {
            super(URI.create("fo:/Test.java"), JavaFileObject.Kind.SOURCE);
            this.text = text;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return text;
        }
    }
}
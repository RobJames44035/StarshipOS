/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8176470
 * @summary javac Pretty printer should include doc comment for modules
 * @modules jdk.compiler
 * @library /tools/lib
 * @build toolbox.TestRunner
 * @run main TestPrettyDocComment
 */

import java.io.IOException;
import java.io.StringWriter;
import java.net.URI;
import java.util.List;

import javax.tools.JavaFileObject;
import javax.tools.JavaCompiler;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;

import toolbox.TestRunner;

public class TestPrettyDocComment extends TestRunner {

    public static void main(String... args) throws Exception {
        TestPrettyDocComment t = new TestPrettyDocComment();
        t.runTests();
    }

    final JavaCompiler tool = ToolProvider.getSystemJavaCompiler();

    TestPrettyDocComment() {
        super(System.err);
    }

    @Test
    public void testModule() throws IOException {
        test("module-info.java", "/** This is a module. */ module m { }");
    }

    @Test
    public void testPackage() throws IOException {
        test("package-info.java", "/** This is a package. */ package p;");
    }

    @Test
    public void testClass() throws IOException {
        test("C.java", "/** This is a class. */ class C { }");
    }

    @Test
    public void testField() throws IOException {
        test("C.java", "class C { /** This is a field. */ int f; }");
    }

    @Test
    public void testMethod() throws IOException {
        test("C.java", "class C { /** This is a method. */ void m() { } }");
    }

    void test(String name, String source) throws IOException {
        JavaFileObject fo = new JavaSource(name, source);
        StringWriter log = new StringWriter();
        JavacTask t = (JavacTask) tool.getTask(log, null, null, null, null, List.of(fo));
        Iterable<? extends CompilationUnitTree> trees = t.parse();
        String out = log.toString();
        if (!out.isEmpty()) {
            System.err.println(log);
        }
        String pretty = trees.iterator().next().toString();
        System.err.println("Pretty: <<<");
        System.err.println(pretty);
        System.err.println(">>>");

        String commentText = source.replaceAll(".*\\Q/**\\E (.*) \\Q*/\\E.*", "$1");
        if (!pretty.contains(commentText)) {
            error("expected text not found: " + commentText);
        }
    }

    static class JavaSource extends SimpleJavaFileObject {
        final String source;

        public JavaSource(String name, String source) {
            super(URI.create("myfo:/" + name), JavaFileObject.Kind.SOURCE);
            this.source = source;
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return source;
        }
    }
}

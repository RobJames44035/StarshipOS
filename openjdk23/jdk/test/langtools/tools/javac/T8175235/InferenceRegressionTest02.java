/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8175235
 * @summary type inference regression after JDK-8046685
 * @library /tools/javac/lib
 * @modules jdk.compiler/com.sun.source.util
 *          jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.code
 *          jdk.compiler/com.sun.tools.javac.file
 *          jdk.compiler/com.sun.tools.javac.tree
 *          jdk.compiler/com.sun.tools.javac.util
 * @build DPrinter
 * @run main InferenceRegressionTest02
 */

import java.io.*;
import java.net.URI;
import java.util.Arrays;

import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.SimpleJavaFileObject;
import javax.tools.ToolProvider;

import com.sun.source.tree.CompilationUnitTree;
import com.sun.source.util.JavacTask;
import com.sun.source.util.Trees;
import com.sun.tools.javac.api.JavacTrees;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Assert;
import com.sun.tools.javac.util.Context;

public class InferenceRegressionTest02 {
    public static void main(String... args) throws Exception {
        new InferenceRegressionTest02().run();
    }

    void run() throws Exception {
        Context context = new Context();
        JavacFileManager.preRegister(context);
        Trees trees = JavacTrees.instance(context);
        StringWriter strOut = new StringWriter();
        PrintWriter pw = new PrintWriter(strOut);
        DPrinter dprinter = new DPrinter(pw, trees);
        final JavaCompiler tool = ToolProvider.getSystemJavaCompiler();
        JavacTask ct = (JavacTask)tool.getTask(null, null, null, null, null, Arrays.asList(new JavaSource()));
        Iterable<? extends CompilationUnitTree> elements = ct.parse();
        ct.analyze();
        Assert.check(elements.iterator().hasNext());
        dprinter.treeTypes(true).printTree("", (JCTree)elements.iterator().next());
        String output = strOut.toString();
        Assert.check(!output.contains("java.lang.Object"), "there shouldn't be any type instantiated to Object");
    }

    static class JavaSource extends SimpleJavaFileObject {

        String source =
                "import java.util.function.*;\n" +
                "import java.util.*;\n" +
                "import java.util.stream.*;\n" +

                "class Foo {\n" +
                "    void test(List<Map.Entry<Foo, Foo>> ls) {\n" +
                "        Map<Foo, Set<Foo>> res = ls.stream()\n" +
                "                .collect(Collectors.groupingBy(Map.Entry::getKey,\n" +
                "                        HashMap::new,\n" +
                "                        Collectors.mapping(Map.Entry::getValue, Collectors.toSet())));\n" +
                "    }\n" +
                "}";

        public JavaSource() {
            super(URI.create("myfo:/Foo.java"), JavaFileObject.Kind.SOURCE);
        }

        @Override
        public CharSequence getCharContent(boolean ignoreEncodingErrors) {
            return source;
        }
    }
}

/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/*
 * @test
 * @bug 8013394
 * @summary compile of iterator use fails with error "defined in an inaccessible class or interface"
 * @library /tools/lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @build toolbox.ToolBox toolbox.JavacTask
 * @run main CompileErrorWithIteratorTest
 */

import toolbox.JavacTask;
import toolbox.ToolBox;

public class CompileErrorWithIteratorTest {

    private static final String TestCollectionSrc =
        "package pkg;\n" +

        "import java.util.Iterator;\n" +
        "import java.util.NoSuchElementException;\n" +

        "public class TestCollection<E> implements Iterable<E> {\n" +
        "    public testCollectionIterator iterator() {\n" +
        "        return  new testCollectionIterator();\n" +
        "    }\n" +
        "    class testCollectionIterator implements Iterator<E> {\n" +
        "        public boolean hasNext() { return true; }\n" +
        "        public E next() throws NoSuchElementException\n" +
        "        {\n" +
        "            return null;\n" +
        "        }\n" +
        "        public void remove() {}\n" +
        "    }\n" +
        "}";

    private static final String TestSrc =
        "import pkg.TestCollection;\n" +
        "\n" +
        "public class Test {\n" +
        "\n" +
        "    public static void main(String[] args) {\n" +
        "        TestCollection<String>  tc1 = new TestCollection<String>();\n" +
        "        for (String s : tc1) {\n" +
        "            System.out.println(s);\n" +
        "        }\n" +
        "      }\n" +
        "}";

    public static void main(String args[]) throws Exception {
        new CompileErrorWithIteratorTest().run();
    }

    ToolBox tb = new ToolBox();

    void run() throws Exception {
        compile();
    }

    void compile() throws Exception {
        new JavacTask(tb)
                .sources(TestCollectionSrc, TestSrc)
                .run();
    }

}

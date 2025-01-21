/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 8208531
 * @summary -javafx mode should be on by default when JavaFX is available.
 * @library /tools/lib ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.*
 * @run main TestJavaFxMode
 */

import toolbox.ToolBox;

import java.nio.file.Path;
import java.nio.file.Paths;

import javadoc.tester.JavadocTester;

public class TestJavaFxMode extends JavadocTester {

    final ToolBox tb;

    public static void main(String... args) throws Exception {
        var tester = new TestJavaFxMode();
        if (tester.sanity()) {
            tester.runTests();
        }
    }

    TestJavaFxMode() {
        tb = new ToolBox();
    }

    // Check if FX modules are available.
    boolean sanity() {
        try {
            Class.forName("javafx.beans.Observable");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Note: javafx.beans.Observable: not found, test passes vacuously");
            return false;
        }
        return true;
    }

    @Test
    public void test(Path base) throws Exception {
        Path src = base.resolve("src");
        createTestClass(src);
        Path outDir = base.resolve("out");

        javadoc("-d", outDir.toString(),
                "-sourcepath", src.toString(),
                "pkg");

        checkExit(Exit.OK);
        checkOrder("pkg/A.html",
                "Property Summary",
                """
                    <a href="#propProperty" class="member-name-link">prop</a>""",
                "Field Summary",
                """
                    <a href="#prop" class="member-name-link">prop</a>""",
                "Method Summary",
                """
                    <a href="#getProp()" class="member-name-link">getProp</a>""",
                """
                    <a href="#propProperty()" class="member-name-link">propProperty</a>""");
    }

    void createTestClass(Path src) throws Exception {
        tb.writeJavaFiles(src,
                """
                    package pkg;
                    import javafx.beans.property.Property;
                    public class A {
                        public Property prop;
                        public Property propProperty(){return null;}
                        public Property getProp(){return null;}
                        public void setProp(Property prop){}
                    }""");
    }

}

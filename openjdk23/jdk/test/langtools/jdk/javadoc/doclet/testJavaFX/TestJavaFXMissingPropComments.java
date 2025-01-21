/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8269774
 * @summary doclint reports missing javadoc comments for JavaFX properties if the docs are on the property method
 * @library /tools/lib ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build toolbox.ToolBox javadoc.tester.*
 * @run main TestJavaFXMissingPropComments
 */

import java.nio.file.Path;

import javadoc.tester.JavadocTester;
import toolbox.ToolBox;

public class TestJavaFXMissingPropComments extends JavadocTester {

    public static void main(String... args) throws Exception {
        var tester = new TestJavaFXMissingPropComments();
        tester.runTests();
    }

    ToolBox tb = new ToolBox();

    @Test
    public void testMissingFieldComments(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src, """
                package p;
                /** Class comment. */
                public class C {
                    /** Dummy class. */
                    public static class BooleanProperty { }

                    // no comment
                    private BooleanProperty value;

                    /**
                     * The value property (property method comment).
                     * @return the property object
                     */
                    public BooleanProperty valueProperty() {
                        return value;
                    }

                    // no comment
                    public boolean getValue() {
                        return true;
                    }
                }
                """);

        javadoc("-d", base.resolve("api").toString(),
                "--javafx",
                "--disable-javafx-strict-checks",
                "-sourcepath", src.toString(),
                "p"
                );
        checkExit(Exit.OK);

        // no warnings for any missing comments
        checkOutput(Output.OUT, false,
                "warning: no comment");

        checkOutput("p/C.html", true,
                """
                    <section class="detail" id="getValue()">
                    <h3>getValue</h3>
                    <div class="horizontal-scroll">
                    <div class="member-signature"><span class="modifiers">public</span>&nbsp;<span c\
                    lass="return-type">boolean</span>&nbsp;<span class="element-name">getValue</span\
                    >()</div>
                    <div class="block">Gets the value of the <code>value</code> property.</div>
                    <dl class="notes">
                    <dt>Property description:</dt>
                    <dd>The value property (property method comment).</dd>
                    <dt>Returns:</dt>
                    <dd>the value of the <code>value</code> property</dd>
                    <dt>See Also:</dt>
                    <dd>
                    <ul class="tag-list">
                    <li><a href="#valueProperty()"><code>valueProperty()</code></a></li>
                    </ul>
                    </dd>
                    </dl>
                    </div>
                    </section>"""
                );
    }

    @Test
    public void testWithFieldComments(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src, """
                package p;
                /** Class comment. */
                public class C {
                    /** Dummy class. */
                    public static class BooleanProperty { }

                    /** The value property (field comment). */
                    private BooleanProperty value;

                    /**
                     * The value property (property method comment).
                     * @return the property object
                     */
                    public BooleanProperty valueProperty() {
                        return value;
                    }

                    // no comment
                    public boolean getValue() {
                        return true;
                    }
                }
                """);

        javadoc("-d", base.resolve("api").toString(),
                "--javafx",
                "--disable-javafx-strict-checks",
                "-sourcepath", src.toString(),
                "p"
        );
        checkExit(Exit.OK);

        // no warnings for any missing comments
        checkOutput(Output.OUT, false,
                "warning: no comment");

        checkOutput("p/C.html", true,
                """
                    <section class="detail" id="getValue()">
                    <h3>getValue</h3>
                    <div class="horizontal-scroll">
                    <div class="member-signature"><span class="modifiers">public</span>&nbsp;<span c\
                    lass="return-type">boolean</span>&nbsp;<span class="element-name">getValue</span\
                    >()</div>
                    <div class="block">Gets the value of the <code>value</code> property.</div>
                    <dl class="notes">
                    <dt>Property description:</dt>
                    <dd>The value property (field comment).</dd>
                    <dt>Returns:</dt>
                    <dd>the value of the <code>value</code> property</dd>
                    <dt>See Also:</dt>
                    <dd>
                    <ul class="tag-list">
                    <li><a href="#valueProperty()"><code>valueProperty()</code></a></li>
                    </ul>
                    </dd>
                    </dl>
                    </div>
                    </section>"""
        );
    }
}

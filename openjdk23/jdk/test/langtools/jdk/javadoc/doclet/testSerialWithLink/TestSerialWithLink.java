/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

/*
 * @test
 * @bug 8288624
 * @summary Test at-serial with {at-link}
 * @library /tools/lib ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.* toolbox.ToolBox
 * @run main TestSerialWithLink
 */

import java.nio.file.Path;

import toolbox.ToolBox;

import javadoc.tester.JavadocTester;

public class TestSerialWithLink extends JavadocTester {
    public static void main(String... args) throws Exception {
        var tester = new TestSerialWithLink();
        tester.runTests();
    }

    private final ToolBox tb;

    TestSerialWithLink() {
        tb = new ToolBox();
    }

    @Test
    public void testSerialWithLink(Path base) throws Exception {
        Path src = base.resolve("src");
        tb.writeJavaFiles(src,
                """
                        package p;
                        import java.io.Serializable;
                        import java.io.ObjectStreamField;
                        /** Comment, */
                        public class C implements Serializable {
                            /** Comment. */
                            C() { }
                            /**
                             * The serial persistent fields for this class.
                             * @serialField item Item An {@link Item} to be serialized.
                             */
                            private static final ObjectStreamField[] serialPersistentFields =
                                { new ObjectStreamField("item", Item.class) };
                            /**
                             * An item that is described in serialPersistentFields.
                             */
                            private Item item;

                            /** A dummy item, not described in serialPersistentFields. */
                            private int dummy;
                        }
                        """, """
                        package p;
                        import java.io.Serializable;
                        /** Comment. */
                        public class Item implements Serializable {
                            /**
                             * Comment.
                             */
                            Item() { }
                        }
                        """);

        javadoc("-d", base.resolve("api").toString(),
                "-sourcepath", src.toString(),
                "--no-platform-links",
                "p");

        checkOutput("serialized-form.html", true,
                """
                    <section class="detail">
                    <h4>Serialized Fields</h4>
                    <ul class="block-list">
                    <li class="block-list">
                    <h5>item</h5>
                    <pre><a href="p/Item.html" title="class in p">Item</a> item</pre>
                    <div class="block">An <a href="p/Item.html" title="class in p"><code>Item</code></a> to be serialized.</div>
                    </li>
                    </ul>
                    </section>""");

    }
}

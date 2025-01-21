/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

 /*
  * @test
  * @bug 8253700
  * @summary spurious "extends Throwable" at end of method declaration
  * throws section.  Make sure that the link is below a Throws heading.
  * @library /tools/lib ../../lib
  * @modules jdk.javadoc/jdk.javadoc.internal.tool
  * @build javadoc.tester.* toolbox.ToolBox
  * @run main TestThrows
  */

 import java.io.IOException;
 import java.nio.file.Path;

 import toolbox.ToolBox;

 import javadoc.tester.JavadocTester;

 public class TestThrows extends JavadocTester {

     public static void main(String... args) throws Exception {
         var tester = new TestThrows();
         tester.runTests();
     }

     private final ToolBox tb = new ToolBox();

     @Test
     public void testThrowsWithBound(Path base) throws IOException {
         Path src = base.resolve("src");
         tb.writeJavaFiles(src,
                 """
                     /**
                      * This is interface C.
                      */
                     public interface C {
                         /**
                          * Method m.
                          * @param <T> the throwable
                          * @throws T if a specific error occurs
                          * @throws Exception if an exception occurs
                          */
                         <T extends Throwable> void m() throws T, Exception;
                     }
                     """);

         javadoc("-d", base.resolve("out").toString(),
                 "--no-platform-links",
                 src.resolve("C.java").toString());
         checkExit(Exit.OK);

         checkOutput("C.html", true,
                 """
                     <div class="member-signature"><span class="type-parameters">&lt;T extends java\
                     .lang.Throwable&gt;</span>&nbsp;<span class="return-type">void</span>&nbsp;<sp\
                     an class="element-name">m</span>()
                                                     throws <span class="exceptions">T,
                     java.lang.Exception</span></div>
                     """,
                 """
                     <dl class="notes">
                     <dt>Type Parameters:</dt>
                     <dd><span id="m()-type-param-T"><code>T</code> - the throwable</span></dd>
                     <dt>Throws:</dt>
                     <dd><code>T</code> - if a specific error occurs</dd>
                     <dd><code>java.lang.Exception</code> - if an exception occurs</dd>
                     </dl>
                     """);
     }
}

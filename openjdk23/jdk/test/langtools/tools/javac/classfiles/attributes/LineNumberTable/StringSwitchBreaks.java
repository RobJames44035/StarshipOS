/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8261606
 * @summary Tests a line number table attribute for language constructions in different containers.
 * @library /tools/lib /tools/javac/lib ../lib
 * @modules jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 *          jdk.compiler/com.sun.tools.javac.util
 *          java.base/jdk.internal.classfile.impl
 * @build toolbox.ToolBox InMemoryFileManager TestBase
 * @build LineNumberTestBase TestCase
 * @run main StringSwitchBreaks
 */

import java.util.List;

public class StringSwitchBreaks extends LineNumberTestBase {
    public static void main(String[] args) throws Exception {
        new StringSwitchBreaks().test();
    }

    public void test() throws Exception {
        test(List.of(TEST_CASE));
    }

    private static final TestCase[] TEST_CASE = new TestCase[] {
        new TestCase("""
                     public class StringSwitchBreaks {                     // 1
                         private void test(String s) {                     // 2
                             if (s != null) {                              // 3
                                 switch (s) {                              // 4
                                     case "a":                             // 5
                                         System.out.println("a");          // 6
                                         break;                            // 7
                                     default:                              // 8
                                         System.out.println("default");    // 9
                                 }                                         //10
                             } else {                                      //11
                                 System.out.println("null");               //12
                             }                                             //13
                         }                                                 //14
                     }                                                     //15
                     """,
                     List.of(1, 3, 4, 6, 7, 9, 10, 12, 14),
                     "StringSwitchBreaks")
    };

}

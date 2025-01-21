/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

/*
 * @test
 * @bug     6289436
 * @summary com.sun.tools.javac.util.List.from(A[]) shouldn't be deprecated
 * @author  Peter von der Ah\u00e9
 * @library ../..
 * @modules jdk.compiler/com.sun.tools.javac.util
 * @compile ../../util/list/FromArray.java
 * @run main util.list.FromArray
 */

package util.list;

import com.sun.tools.javac.util.List;

public class FromArray {
    public static void test(String... args) {
        List<String> ss = List.from(args);
        if (args != null) {
            for (String s : args) {
                if (s != ss.head)
                    throw new AssertionError("s != ss.head (" + s + ", " + ss.head + ")");
                ss = ss.tail;
            }
        }
        if (!ss.isEmpty())
            throw new AssertionError("!ss.isEmpty (" + ss + ")");
    }
    public static void main(String... args) {
        test();
        test((String[])null);
        test("foo");
        test("foo", "bar");
        test("foo", "bar", "bax", "qux", "hest", "fisk", "ko", "fugl");
        System.out.println("<A>List.from(A[]) test OK");
    }
}

/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/**
 * @test
 * @summary Test implicit string concat calls argument conversions in the right order
 * @bug 8200118
 *
 * @compile ImplicitStringConcatOrder.java
 * @run main/othervm -Xverify:all ImplicitStringConcatOrder
 *
 * @compile -XDstringConcat=inline ImplicitStringConcatOrder.java
 * @run main/othervm -Xverify:all ImplicitStringConcatOrder
 *
 * @compile -XDstringConcat=indy ImplicitStringConcatOrder.java
 * @run main/othervm -Xverify:all ImplicitStringConcatOrder
 *
 * @compile -XDstringConcat=indyWithConstants ImplicitStringConcatOrder.java
 * @run main/othervm -Xverify:all ImplicitStringConcatOrder
*/
import java.lang.StringBuilder;

public class ImplicitStringConcatOrder {

    static MyClass c = new MyClass();

    public static void main(String[] args) throws Exception {
        test("foo123bar",    "foo" + c + c + c + "bar");
        test("bazxyz456abc", "baz" + ("xyz" + c + c) + c + "abc");
        test("caf7eba89be",  "caf" + c + ("eba" + c + c) + "be");
    }

    public static void test(String expected, String actual) {
       if (!expected.equals(actual)) {
           StringBuilder sb = new StringBuilder();
           sb.append("Expected = ");
           sb.append(expected);
           sb.append(", actual = ");
           sb.append(actual);
           throw new IllegalStateException(sb.toString());
       }
    }

    static class MyClass {
        int x;
        public String toString() {
            return String.valueOf(++x);
        }
    }
}

/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @library /test/lib
 * @requires vm.flavor != "minimal"
 * @run main/othervm/native -agentlib:SimpleClassFileLoadHook=Foo,XXX,YYY
 *      SimpleClassFileLoadHookTest
 */
import jdk.test.lib.Asserts;

class Foo {
    static String getValue() {
        return "XXX";
    }
    static String getOtherValue() {
        return "xXXXxx";
    }
}
public class SimpleClassFileLoadHookTest {
    public static void main(String args[]) {
        System.out.println(Foo.getValue());
        System.out.println(Foo.getOtherValue());
        Asserts.assertTrue("YYY".equals(Foo.getValue()) &&
                           "xYYYxx".equals(Foo.getOtherValue()),
                           "SimpleClassFileLoadHook should replace XXX with YYY");
    }
}

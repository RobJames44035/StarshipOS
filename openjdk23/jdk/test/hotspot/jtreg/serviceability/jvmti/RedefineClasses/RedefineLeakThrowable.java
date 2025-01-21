/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */

/*
 * @test
 * @bug 8308762
 * @library /test/lib
 * @summary Test that redefinition of class containing Throwable refs does not leak constant pool
 * @requires vm.jvmti
 * @requires vm.flagless
 * @modules java.base/jdk.internal.misc
 * @modules java.instrument
 *          java.compiler
 * @run main RedefineClassHelper
 * @run main/othervm/timeout=6000 -javaagent:redefineagent.jar -XX:MetaspaceSize=25m -XX:MaxMetaspaceSize=25m RedefineLeakThrowable
 */

// MaxMetaspaceSize=25m allows InMemoryJavaCompiler to load even if CDS is off.
class Tester {
    void test() {
        try {
            int i = 42;
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

public class RedefineLeakThrowable {

    static final String NEW_TESTER =
        "class Tester {" +
        "   void test() {" +
        "        try {" +
        "            int i = 42;" +
        "        } catch (Throwable t) {" +
        "            t.printStackTrace();" +
        "        }" +
        "    }" +
        "}";


    public static void main(String argv[]) throws Exception {
        for (int i = 0; i < 700; i++) {
            RedefineClassHelper.redefineClass(Tester.class, NEW_TESTER);
        }
    }
}

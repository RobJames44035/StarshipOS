/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8197901 8209758
 * @summary Redefine classes with enabling logging to verify Klass:external_name() during GC.
 * @comment This test is simplified version of serviceability/jvmti/RedefineClasses/RedefineRunningMethods.java.
 * @library /test/lib
 * @modules java.compiler
 *          java.instrument
 * @requires vm.jvmti
 * @run main RedefineClassHelper
 * @run main/othervm -Xmx256m -XX:MaxMetaspaceSize=64m -javaagent:redefineagent.jar -Xlog:all=trace:file=all.log RedefineClasses
 */

// package access top-level class to avoid problem with RedefineClassHelper
// and nested types.
class RedefineClasses_B {
    public static void test() {
    }
}

public class RedefineClasses {
    static Object[] obj = new Object[20];
    public static String newB =
            "class RedefineClasses_B {" +
            "    public static void test() { " +
            "    }" +
            "}";

    public static void main(String[] args) throws Exception {
        RedefineClassHelper.redefineClass(RedefineClasses_B.class, newB);
        RedefineClasses_B.test();
        for (int i = 0; i < 20 ; i++) {
            obj[i] = new byte[1024 * 1024];
            System.gc();
        }
    }
}

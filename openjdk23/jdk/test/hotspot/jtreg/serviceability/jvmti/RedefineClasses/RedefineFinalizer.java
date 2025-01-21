/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 6904403 8010319
 * @summary Don't assert if we redefine finalize method
 * @requires vm.jvmti
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @modules java.compiler
 *          java.instrument
 *          jdk.jartool/sun.tools.jar
 * @run main RedefineClassHelper
 * @run main/othervm -javaagent:redefineagent.jar RedefineFinalizer
 */

/*
 * Regression test for hitting:
 *
 * assert(f == k->has_finalizer()) failed: inconsistent has_finalizer
 *
 * when redefining finalizer method
 */


// package access top-level class to avoid problem with RedefineClassHelper
// and nested types.
class RedefineFinalizer_B {
    @SuppressWarnings("removal")
    protected void finalize() {
        // should be empty
    }
}

public class RedefineFinalizer {

    public static String newB = """
                class RedefineFinalizer_B {
                    protected void finalize() {
                        System.out.println("Finalizer called");
                    }
                }
                """;

    public static void main(String[] args) throws Exception {
        RedefineClassHelper.redefineClass(RedefineFinalizer_B.class, newB);

        A a = new A();
    }

    static class A extends RedefineFinalizer_B {
    }
}

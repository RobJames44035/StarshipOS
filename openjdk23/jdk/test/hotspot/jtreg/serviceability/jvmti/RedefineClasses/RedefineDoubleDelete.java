/*
 * StarshipOS Copyright (c) 2017-2025. R.A. James
 */

/*
 * @test
 * @bug 8178870 8010319
 * @summary Redefine class with CFLH twice to test deleting the cached_class_file
 * @requires vm.jvmti
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @modules java.compiler
 *          java.instrument
 *          jdk.jartool/sun.tools.jar
 * @run main RedefineClassHelper
 * @run main/othervm/native -Xlog:redefine+class+load+exceptions -agentlib:RedefineDoubleDelete -javaagent:redefineagent.jar RedefineDoubleDelete
 */

// package access top-level class to avoid problem with RedefineClassHelper
// and nested types.

// The ClassFileLoadHook for this class turns foo into faa and prints out faa.
class RedefineDoubleDelete_B {
    int faa() { System.out.println("foo"); return 1; }
}

public class RedefineDoubleDelete {

    // Class gets a redefinition error because it adds a data member
    public static String newB = """
                class RedefineDoubleDelete_B {
                   int count1 = 0;
                }
                """;

    public static String newerB = """
                class RedefineDoubleDelete_B {
                   int faa() { System.out.println("baa"); return 2; }
                }
                """;

    public static void main(String args[]) throws Exception {

        RedefineDoubleDelete_B b = new RedefineDoubleDelete_B();
        int val = b.faa();
        if (val != 1) {
            throw new RuntimeException("return value wrong " + val);
        }

        // Redefine B twice to get cached_class_file in both B scratch classes
        try {
            RedefineClassHelper.redefineClass(RedefineDoubleDelete_B.class, newB);
        } catch (java.lang.UnsupportedOperationException e) {
            // this is expected
        }
        try {
            RedefineClassHelper.redefineClass(RedefineDoubleDelete_B.class, newB);
        } catch (java.lang.UnsupportedOperationException e) {
            // this is expected
        }

        // Do a full GC.
        System.gc();

        // Redefine with a compatible class
        RedefineClassHelper.redefineClass(RedefineDoubleDelete_B.class, newerB);
        val = b.faa();
        if (val != 2) {
            throw new RuntimeException("return value wrong " + val);
        }

        // Do another full GC to clean things up.
        System.gc();
    }
}

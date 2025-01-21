/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8081800 8010319
 * @summary Redefine private and default interface methods
 * @requires vm.jvmti
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @modules java.compiler
 *          java.instrument
 *          jdk.jartool/sun.tools.jar
 * @run main RedefineClassHelper
 * @run main/othervm -javaagent:redefineagent.jar -Xlog:redefine+class*=trace RedefineInterfaceMethods
 */

// package access top-level class to avoid problem with RedefineClassHelper
// and nested types.

interface RedefineInterfaceMethods_B {
    int ORIGINAL_RETURN = 1;
    int NEW_RETURN = 2;
    private int privateMethod() {
        return ORIGINAL_RETURN;
    }
    public default int defaultMethod() {
        return privateMethod();
    }
}

public class RedefineInterfaceMethods {

    static final int RET = -2;

    public static String redefinedPrivateMethod = """
        interface RedefineInterfaceMethods_B {
            int ORIGINAL_RETURN = 1;
            int NEW_RETURN = 2;
            private int privateMethod() {
                return NEW_RETURN;
            }
            public default int defaultMethod() {
                return privateMethod();
            }
        }
        """;

    public static String redefinedDefaultMethod = """
        interface RedefineInterfaceMethods_B {
            int ORIGINAL_RETURN = 1;
            int NEW_RETURN = 2;
            private int privateMethod() {
                return ORIGINAL_RETURN;
            }
            public default int defaultMethod() {
                return RedefineInterfaceMethods.RET;
            }
        }
        """;

    static class Impl implements RedefineInterfaceMethods_B {
    }


    public static void main(String[] args) throws Exception {

        Impl impl = new Impl();

        int res = impl.defaultMethod();
        if (res != RedefineInterfaceMethods_B.ORIGINAL_RETURN)
            throw new Error("defaultMethod returned " + res +
                            " expected " + RedefineInterfaceMethods_B.ORIGINAL_RETURN);

        RedefineClassHelper.redefineClass(RedefineInterfaceMethods_B.class, redefinedPrivateMethod);

        res = impl.defaultMethod();
        if (res != RedefineInterfaceMethods_B.NEW_RETURN)
            throw new Error("defaultMethod returned " + res +
                            " expected " + RedefineInterfaceMethods_B.NEW_RETURN);

        System.gc();

        RedefineClassHelper.redefineClass(RedefineInterfaceMethods_B.class, redefinedDefaultMethod);

        res = impl.defaultMethod();
        if (res != RET)
            throw new Error("defaultMethod returned " + res +
                            " expected " + RET);
    }
}

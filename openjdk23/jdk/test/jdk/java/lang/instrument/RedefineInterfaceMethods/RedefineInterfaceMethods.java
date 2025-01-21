/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8225325
 * @summary Add tests for redefining a class' private method during resolution of the bootstrap specifier
 * @library /test/lib
 * @modules java.base/jdk.internal.misc
 * @modules java.compiler
 *          java.instrument
 * @compile ../NamedBuffer.java
 * @compile redef/Xost.java
 * @run main RedefineClassHelper
 * @run main/othervm -javaagent:redefineagent.jar -Xlog:redefine+class*=trace RedefineInterfaceMethods
 */

class Host {
    static void log(String msg) { System.out.println(msg); }

    static interface B {
        int ORIGINAL_RETURN = 1;
        int NEW_RETURN = 2;

        private int privateMethod() {
            Runnable race1 = () -> log("Hello from inside privateMethod");
            race1.run();
            return ORIGINAL_RETURN;
        }

        public default int defaultMethod(String p) {
            log(p + "from interface B's defaultMethod");
            return privateMethod();
        }
    }

    static class Impl implements B {
    }
}

public class RedefineInterfaceMethods {
    private static byte[] bytesForHostClass(char replace) throws Throwable {
        return NamedBuffer.bytesForHostClass(replace, "Host$B");
    }

    public static void main(String[] args) throws Throwable {
        Host.Impl impl = new Host.Impl();

        int res = impl.defaultMethod("Hello ");
        if (res != Host.B.ORIGINAL_RETURN)
            throw new Error("defaultMethod returned " + res +
                            " expected " + Host.B.ORIGINAL_RETURN);

        byte[] buf = bytesForHostClass('X');
        RedefineClassHelper.redefineClass(Host.B.class, buf);

        res = impl.defaultMethod("Goodbye ");
        if (res != Host.B.NEW_RETURN)
            throw new Error("defaultMethod returned " + res +
                            " expected " + Host.B.NEW_RETURN);
    }
}

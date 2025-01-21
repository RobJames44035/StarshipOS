/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8323243
 * @summary Test that invocation of an abstract method from JNI works correctly
 * @compile AbstractMethodClass.jasm
 * @run main/othervm/native TestJNIAbstractMethod
 */

/**
 * We are testing invocation of an abstract method from JNI - which should
 * simply result in throwning AbstractMethodError. To invoke an abstract method
 * we must have an instance method (as abstract static methods are illegal),
 * but instantiating an abstract class is also illegal at the Java language
 * level, so we have to use a custom jasm class that contains an abstract method
 * declaration, but which is not itself declared as an abstract class.
 */
public class TestJNIAbstractMethod {

    // Invokes an abstract method from JNI and throws AbstractMethodError.
    private static native void invokeAbstractM(Class<?> AMclass,
                                               AbstractMethodClass receiver);

    static {
        System.loadLibrary("JNIAbstractMethod");
    }

    public static void main(String[] args) {
        AbstractMethodClass obj = new AbstractMethodClass();
        try {
            System.out.println("Attempting direct invocation via Java");
            obj.abstractM();
            throw new RuntimeException("Did not get AbstractMethodError from Java!");
        } catch (AbstractMethodError expected) {
            System.out.println("ok - got expected exception: " + expected);
        }
        try {
            System.out.println("Attempting direct invocation via JNI");
            invokeAbstractM(obj.getClass(), obj);
            throw new RuntimeException("Did not get AbstractMethodError from JNI!");
        } catch (AbstractMethodError expected) {
            System.out.println("ok - got expected exception: " + expected);
        }
    }
}

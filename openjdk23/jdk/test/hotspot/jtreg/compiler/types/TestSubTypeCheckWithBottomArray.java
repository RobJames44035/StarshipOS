/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test id=Xcomp
 * @bug 8328702
 * @summary Check that SubTypeCheckNode is properly folded when having an array with bottom type elements checked
 *          against an interface.
 *
 * @run main/othervm -Xcomp -XX:CompileCommand=compileonly,compiler.types.TestSubTypeCheckWithBottomArray::test*
 *                   -XX:CompileCommand=inline,compiler.types.TestSubTypeCheckWithBottomArray::check*
 *                   compiler.types.TestSubTypeCheckWithBottomArray
 */

/*
 * @test id=Xbatch
 * @bug 8328702
 * @summary Check that SubTypeCheckNode is properly folded when having an array with bottom type elements checked
 *          against an interface.
 *
 * @run main/othervm -Xbatch -XX:CompileCommand=compileonly,compiler.types.TestSubTypeCheckWithBottomArray::test*
 *                   -XX:CompileCommand=inline,compiler.types.TestSubTypeCheckWithBottomArray::check*
 *                   compiler.types.TestSubTypeCheckWithBottomArray
 */

/*
 * @test id=stress
 * @bug 8328702
 * @summary Check that PartialSubtypeCheckNode is properly folded when having an array with bottom type elements checked
 *          either against an interface or an unrelated non-sub-class.
 *
 * @run main/othervm -Xcomp -XX:CompileCommand=compileonly,compiler.types.TestSubTypeCheckWithBottomArray::test*
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:+StressReflectiveCode
 *                   -XX:CompileCommand=inline,compiler.types.TestSubTypeCheckWithBottomArray::check*
 *                   compiler.types.TestSubTypeCheckWithBottomArray
 * @run main/othervm -Xbatch -XX:CompileCommand=compileonly,compiler.types.TestSubTypeCheckWithBottomArray::test*
 *                   -XX:+IgnoreUnrecognizedVMOptions -XX:+StressReflectiveCode
 *                   -XX:CompileCommand=inline,compiler.types.TestSubTypeCheckWithBottomArray::check*
 *                   compiler.types.TestSubTypeCheckWithBottomArray
 */

package compiler.types;

public class TestSubTypeCheckWithBottomArray {
    static byte[] bArr = new byte[10];
    static Object[] oArr = new Object[10];
    static boolean flag;

    public static void main(String[] args) {
        A a = new A();
        B b = new B();
        Y y = new Y();
        Z z = new Z();
        for (int i = 0; i < 10000; i++) {
            // With -Xcomp: Immediatly crashes because of no profiling -> don't know anything.
            checkInterface(a); // Make sure that checkInterface() sometimes passes instanceof.
            checkInterface(b); // Use two sub classes such that checkcast is required.
            testInterface();

            checkClass(y); // Make sure that checkClass() sometimes passes instanceof.
            checkClass(z); // Use two sub classes such that checkcast is required.
            testClass();
            flag = !flag;
        }
    }

    static void testInterface() {
        checkInterface(flag ? bArr : oArr); // Inlined, never passes instanceof
    }

    static void checkInterface(Object o) {
        if (o instanceof I i) {
            // Use of i: Needs CheckCastPP which is replaced by top because [bottom <: I cannot be true.
            // But: SubTypeCheckNode is not folded away -> broken graph (data dies, control not)
            i.getClass();
        }
    }

    static void testClass() {
        checkClass(flag ? bArr : oArr); // Inlined, never passes instanceof
    }

    static void checkClass(Object o) {
        if (o instanceof X x) {
            // Use of i: Needs CheckCastPP which is replaced by top because [bottom <: I cannot be true.
            // But: SubTypeCheckNode is not folded away -> broken graph (data dies, control not)
            x.getClass();
        }
    }

}

interface I {}
class A implements I {}
class B implements I {}

class X {}
class Y extends X {}
class Z extends X {}

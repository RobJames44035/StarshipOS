/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8328480
 * @summary Test that SubTypeCheckNode takes improved unique concrete klass constant in order to fold consecutive sub
 *          type checks.
 * @library /test/lib /
 * @run driver compiler.types.TestSubTypeCheckUniqueSubclass
 */

package compiler.types;

import compiler.lib.ir_framework.*;

public class TestSubTypeCheckUniqueSubclass {
    static Object o = new C(); // Make sure C is loaded.
    static Object o2 = new C2(); // Make sure C2 is loaded while NeverLoaded is not.

    public static void main(String[] args) {
        TestFramework.run();
    }

    @Test
    @Warmup(0)
    @IR(counts = {IRNode.SUBTYPE_CHECK, "1"},
        phase = CompilePhase.ITER_GVN1)
    static void testAbstractAbstract() {
         A a = (A)o;
         A a2 = (B)o;
    }

    @Test
    @Warmup(0)
    @IR(counts = {IRNode.SUBTYPE_CHECK, "1"},
            phase = CompilePhase.ITER_GVN1)
    static void testAbstractAbstractWithUnloaded() {
        A2 a = (A2)o2;
        A2 a2 = (B2)o2;
    }
}

abstract class A {}
abstract class B extends A {}
class C extends B {}

abstract class A2 {}
abstract class B2 extends A2 {}
class C2 extends B2 {}

// Class never loaded -> C2 looks like unique sub class.
class NeverLoaded extends B2 {}

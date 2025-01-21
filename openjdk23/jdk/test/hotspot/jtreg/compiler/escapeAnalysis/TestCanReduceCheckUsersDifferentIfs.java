/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8343380
 * @summary Test that can_reduce_check_users() can handle different If nodes and that we bail out properly if it's not
 *          an actual IfNode.
 * @run main/othervm -XX:CompileCommand=compileonly,compiler.escapeAnalysis.TestCanReduceCheckUsersDifferentIfs::test*
 *                   -Xcomp compiler.escapeAnalysis.TestCanReduceCheckUsersDifferentIfs
 */

package compiler.escapeAnalysis;

public class TestCanReduceCheckUsersDifferentIfs {
    static int iFld, iFld2;
    static boolean flag;

    public static void main(String[] args) {
        // Make sure classes are loaded.
        new B();
        new C();
        testParsePredicate();
        testOuterStripMinedLoopEnd();
    }

    static void testOuterStripMinedLoopEnd() {
        // (1) phi1 for a: phi(CheckCastPP(B), CheckCastPP(c)) with type A:NotNull
        A a = flag ? new B() : new C();

        // (4) Loop removed in PhaseIdealLoop before EA and we know that x == 77.
        int x = 77;
        int y = 0;
        do {
            x--;
            y++;
        } while (x > 0);

        // (L)
        for (int i = 0; i < 100; i++) {
            iFld += 34;
        }
        // (6) CastPP(phi1) ends up at IfFalse of OuterStripMinedLoopEnd of loop (L).
        // (7) EA tries to reduce phi1(CheckCastPP(B), CheckCastPP(c)) and looks at
        //     OuterStripMinedLoopEnd and asserts that if it's not an IfNode that it has
        //     an OpaqueNotNull which obviously is not the case and the assert fails.

        // (5) Found to be false after PhaseIdealLoop before EA and is folded away.
        if (y == 76) {
            a = (B) a; // (2) a = CheckCastPP(phi1)
        }
        // (3) phi2 for a: phi(if, else) = phi(CheckCastPP(phi1), phi1)
        //     phi(CheckCastPP(phi1), phi1) is replaced in PhiNode::Ideal with a CastPP:
        //     a = CastPP(phi1) with type A:NotNull
        iFld2 = a.iFld;
    }

    // Same as testOuterStripMinedLoopEnd() but we find in (7) a ParsePredicate from the
    // removed loop (L) which also does not have an OpaqueNotNull and the assert fails.
    static void testParsePredicate() {
        A a = flag ? new B() : new C();

        int x = 77;
        int y = 0;
        // (L)
        do {
            x--;
            y++;
        } while (x > 0);

        if (y == 76) {
            a = (B) a;
        }
        iFld2 = a.iFld;
    }
}

class A {
    int iFld;
}

class B extends A {
}

class C extends A {
}

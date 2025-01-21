/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4249112 4785453
 * @summary Verify that implicit member modifiers are set correctly.
 *
 * @compile/ref=MemberModifiers.out --debug=dumpmodifiers=cfm MemberModifiers.java
 */

// Currently, we check only that members of final classes are not final.
// Originally, we tested that methods were final, per the fix for 4249112.
// This fix was backed out, however, based on a determination that the
// ACC_FINAL bit need not actually be set, and should not be for compatibility
// reasons.

public final class MemberModifiers {

    //Should not be final.
    int f;
    void m() {};
    class c {};
    interface i {}

}


class MemberModifiersAux {

    final class Foo {

        //Should not be final.
        int f;
        void m() {};
        class c {};

    }

}

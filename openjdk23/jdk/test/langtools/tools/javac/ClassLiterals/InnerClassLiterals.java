/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @bug 4030384
 * @summary Verify inner class can be used in class literal.
 * Also includes a few other sanity checks.
 * @author William Maddox (maddox)
 *
 * @compile InnerClassLiterals.java
 * @run main InnerClassLiterals
 */

public class InnerClassLiterals {

    // Should not generate access errors.
    public static void main(String[] args) {
        Class x1 = int.class;
        Class x2 = float.class;
        Class x3 = void.class;
        Class x4 = String.class;
        Class x5 = Integer.class;
        Class x6 = InnerClassLiterals.class;
        // Bug 4030384: Compiler did not allow this.
        Class x7 = InnerClassLiterals.Inner1.class;
        Class x8 = InnerClassLiterals.Inner2.class;
    }

    class Inner1 {}

    static class Inner2 {}
}

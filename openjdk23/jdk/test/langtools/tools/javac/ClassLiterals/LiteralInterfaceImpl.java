/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/*
 * @bug 4055017
 * @summary Test use of class literals within interfaces.
 * @author William Maddox (maddox)
 *
 * @compile LiteralInterface_1.java
 * @compile LiteralInterface_2.java
 * @compile LiteralInterface_3.java
 * @compile LiteralInterfaceImpl.java
 * @run main LiteralInterfaceImpl
 */

public class LiteralInterfaceImpl
implements LiteralInterface_1, LiteralInterface_2, LiteralInterface_3 {
    private static void check(Class c1, Class c2) throws Exception{
        if (c1 != c2) {
            throw new Exception("mismatch: " + c1 + ", " + c2);
        }
    }
    public static void main(String[] args) throws Exception {

        check(c1, Object.class);
        check(c2, Integer.class);

        check(foo, Object.class);
        check(bar, String.class);
        check(baz, Integer.class);

        // Due to another bug (4119981),
        // J is not inherited from LiteralInterface.
        // check(quux, J.class);

        check(quux, LiteralInterface_2.J.class);

        LiteralInterface_2.J o = new LiteralInterface_2.J();
        check(o.quem, Float.class);

    }
}

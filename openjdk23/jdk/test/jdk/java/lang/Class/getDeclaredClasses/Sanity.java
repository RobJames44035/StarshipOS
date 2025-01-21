/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
 * @bug 4028577 4726689
 * @summary Sanity check that Class.getDeclaredClasses() works.
 */
public class Sanity {
    static class Toplevel { }
    class Nested { }

    public static void main(String[] args) throws Exception {
        class BlockLocal { };
        new Object() { };
        Class[] c = Sanity.class.getDeclaredClasses();
        if (c.length < 2)
             throw new Exception("Incorrect number of declared classes");

        for (int i = 0; i < c.length; i++) {
            String name = c[i].getName();
            System.out.println(name);

            if (c[i] != Nested.class && c[i] != Toplevel.class
                && !name.matches("\\D\\w*\\$\\d*"))
                throw new Exception("Unexpected class: " + name);
        }
    }
}

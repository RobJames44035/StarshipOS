/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4109289
   @summary Turning off access checks now enables illegal reflection.
   @author Anand Palaniswamy
*/

import java.lang.reflect.Method;

/**
 * Try to call a private method with Method.invoke(). If that doesn't
 * throw a IllegalAccessException, then access checks are disabled,
 * which is a bad idea.
 */
public class IllegalAccessInInvoke {
    public static void main(String[] argv) {
        Class[] argTypes = new Class[0];
        Object[] args = new Object[0];
        Method pm = null;

        try {
            pm = Foo.class.getDeclaredMethod("privateMethod", argTypes);
        } catch (NoSuchMethodException nsme) {
            throw new
                RuntimeException("Bizzare: privateMethod *must* be there");
        }

        boolean ethrown = false;
        try {
            pm.invoke(new Foo(), args);
        } catch (IllegalAccessException iae) {
            ethrown = true;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected " + e.toString());
        }

        if (!ethrown) {
            throw new
                RuntimeException("Reflection access checks are disabled");
        }
    }
}

class Foo {
    private void privateMethod() {
    }
}

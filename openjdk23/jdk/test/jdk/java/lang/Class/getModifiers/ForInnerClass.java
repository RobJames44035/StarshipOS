/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4109635
   @summary For an inner class, the access bits must come from the
            InnerClasses attribute, not from the class block's access.
            Note however that the VM should not rely on these access
            flags from the attribute!
 */
import java.lang.reflect.Modifier;
public class ForInnerClass {
    private class Inner {
    }

    protected class Protected {
    }

    public static void main(String[] args) throws Exception {
        /* We are not testing for the ACC_SUPER bug, so strip we strip
         * synchorized. */

        int m = 0;

        m = Inner.class.getModifiers() & (~Modifier.SYNCHRONIZED);
        if (m != Modifier.PRIVATE)
            throw new Exception("Access bits for innerclass not from " +
                                "InnerClasses attribute");

        m = Protected.class.getModifiers() & (~Modifier.SYNCHRONIZED);
        if (m != Modifier.PROTECTED)
            throw new Exception("Protected inner class wronged modifiers");
    }
}

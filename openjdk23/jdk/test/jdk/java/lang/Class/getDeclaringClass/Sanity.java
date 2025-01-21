/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4028577
   @summary Sanity check that Class.getDeclaringClass() works.
   @author Anand Palaniswamy
 */
public class Sanity {
    class Nested  {
    }

    public static void main(String[] args) throws Exception {
        if (Nested.class.getDeclaringClass() != Sanity.class)
            throw new Exception("Not finding declaring class");

        /* This will pass on old VM's that return null when this
         * method was unimplemented. But write the test to keep
         * regression from happening in the current code.
         */
        class BlockLocal {
        };

        if (BlockLocal.class.getDeclaringClass() != null)
            throw new Exception("Finding declaring class for block local");
    }
}

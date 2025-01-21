/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4109635
   @summary VM adds ACC_SUPER bit to access flags of a class. This must
            be stripped by the Class.getModifiers method, or else this
            shows up as though the class is synchronized and that doesn't
            make any sense.
   @author Anand Palaniswamy
 */
public class StripACC_SUPER {
    public static void main(String[] args) throws Exception {
        int access = StripACC_SUPER.class.getModifiers();
        if (java.lang.reflect.Modifier.isSynchronized(access))
            throw new Exception("ACC_SUPER bit is not being stripped");
    }
}

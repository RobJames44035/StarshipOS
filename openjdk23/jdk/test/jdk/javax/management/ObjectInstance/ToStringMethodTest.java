/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 5080083
 * @summary Test new added method "toString"
 * @author Shanliang JIANG
 *
 * @run clean ToStringMethodTest
 * @run build ToStringMethodTest
 * @run main ToStringMethodTest
 */

import javax.management.*;

public class ToStringMethodTest {
    public static void main(String[] args) throws Exception {

        // for ObjectInstance class
        System.out.println(">>> Test on the method \"toString\" of the ObjectInstance class.");

        final ObjectName on = new ObjectName(":key=me");
        final String className = "Unknown";
        final ObjectInstance oi = new ObjectInstance(on, className);

        final String expected = className+"["+on.toString()+"]";

        if (!expected.equals(oi.toString())) {
            throw new RuntimeException("The test failed on the method \"toString\" "+
                                       "of the ObjectInstance class, expected to get "+
                                       expected+", but got "+oi.toString());
        }

        // for Attribute class
        System.out.println(">>> Test on the method \"toString\" of the Attribute class.");
        final String name = "hahaha";
        final Object value = new int[0];
        final String exp = name + " = " + value;

        final Attribute at = new Attribute(name, value);

        if (!exp.equals(at.toString())) {
            throw new RuntimeException("The test failed on  the method \"toString\" "+
                                       "of the Attribute class, expected to get "+exp+
                                       ", but got "+at.toString());
        }

        System.out.println(">>> All passed.");
    }
}

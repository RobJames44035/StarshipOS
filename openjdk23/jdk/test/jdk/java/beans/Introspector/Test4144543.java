/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4144543
 * @summary Tests that introspection handles multiple setters in any order
 * @author Janet Koenig
 */

import java.beans.Beans;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;

public class Test4144543 {
    public static void main(String[] args) throws Exception {
        Class type = Beans.instantiate(null, "Test4144543").getClass();

        // try all the various places that this would break before

        Introspector.getBeanInfo(type);
        new PropertyDescriptor("value", type);
        new PropertyDescriptor("value", type, "getValue", "setValue");
    }

    private int value;

    public int getValue() {
        return this.value;
    }

    /*
     * The Introspector expects the return type of the getter method to
     * match the parameter type of the setter method.  So list the setter
     * method which has a different type (but compatible) first so that
     * the Introspector will find it first and recognize that it is not
     * the correct setter method.
     */

    public void setValue(byte value) {
        this.value = value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

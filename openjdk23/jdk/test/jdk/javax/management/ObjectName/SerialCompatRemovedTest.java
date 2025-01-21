/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/*
 * @test
 * @bug 8334165
 * @summary Test that jmx.serial.form is not recognised.
 *
 * @run main/othervm -Djmx.serial.form=1.0 SerialCompatRemovedTest
 * @run main/othervm SerialCompatRemovedTest
 */

import java.io.*;
import java.util.*;
import javax.management.ObjectName;

public class SerialCompatRemovedTest {

    public static void main(String[] args) throws Exception {
        ObjectStreamClass osc = ObjectStreamClass.lookup(ObjectName.class);
        // Serial form has no fields, uses writeObject, so we should never see
        // non-zero field count here:
        if (osc.getFields().length != 0) {
            throw new Exception("ObjectName using old serial form?: fields: " +
                    Arrays.asList(osc.getFields()));
        }
    }
}


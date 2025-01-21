/*
 * StarshipOS Copyright (c) 2023-2025. R.A. James
 */
/*
 * @test
 * @bug 8316771
 * @library /test/lib
 * @modules java.security.jgss/sun.security.krb5.internal:+open
 * @summary make sure each error code has a message
 */

import jdk.test.lib.Asserts;
import sun.security.krb5.internal.Krb5;

import java.lang.reflect.Field;
import java.util.Hashtable;

public class ErrorMessages {
    public static void main(String[] args) throws Exception {
        boolean isError = false;
        int count = 0;
        int size = -1;
        for (Field v : Krb5.class.getDeclaredFields()) {
            // The spec of the Class::getDeclaredFields method claims
            // "The elements in the returned array are not sorted and
            // are not in any particular order". However, the current
            // implementation seems to be listing them in the order
            // they appear in the code.
            if (v.getName().equals("errMsgList")) {
                v.setAccessible(true);
                size = ((Hashtable)v.get(null)).size();
                break;
            }
            if (v.getName().equals("KDC_ERR_NONE")) {
                isError = true;
            }
            if (!isError) continue;
            Asserts.assertNotEquals(Krb5.getErrorMessage((int)v.get(null)),
                    null, "No msg for " + v);
            count++;
        }
        Asserts.assertEQ(count, size, "Different size");
    }
}

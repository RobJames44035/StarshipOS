/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/*
 * @test
 * @bug 6631361
 * @summary Test constructor when PD array is null or contains all null contexts
 */

import java.security.AccessControlContext;
import java.security.ProtectionDomain;

public class CheckCtor {

    public static void main(String[] args) throws Exception {

        // check that null PD array throws NPE
        try {
            new AccessControlContext(null);
            throw new Exception("Expected NullPointerException not thrown");
        } catch (Exception e) {
            if (!(e instanceof NullPointerException)) {
                throw new Exception("Expected NullPointerException not thrown");
            }
        }

        // check that empty PD array equals PD array of one or more nulls
        ProtectionDomain zero[] = {};
        ProtectionDomain null1[] = {null};
        ProtectionDomain null2[] = {null, null};

        AccessControlContext accZero = new AccessControlContext(zero);
        AccessControlContext accNull1 = new AccessControlContext(null1);
        AccessControlContext accNull2 = new AccessControlContext(null2);

        testEquals(accZero, accNull1);
        testEquals(accZero, accNull2);
        testEquals(accNull1, accNull2);
        testEquals(accNull1, accZero);
        testEquals(accNull2, accZero);
        testEquals(accNull2, accNull1);
    }

    private static void testEquals(AccessControlContext acc1,
        AccessControlContext acc2) throws Exception {
        if (!acc1.equals(acc2)) {
            throw new Exception("AccessControlContexts should be equal");
        }
    }
}

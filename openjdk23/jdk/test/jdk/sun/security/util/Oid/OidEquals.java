/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/*
 * @test
 * @bug 8022444 8242151
 * @summary Test ObjectIdentifier.equals(Object obj)
 * @modules java.base/sun.security.util
 */

import sun.security.util.ObjectIdentifier;

public class OidEquals {
    public static void main(String[] args) throws Exception {
        ObjectIdentifier oid1 = ObjectIdentifier.of("1.3.6.1.4.1.42.2.17");
        ObjectIdentifier oid2 = ObjectIdentifier.of("1.2.3.4");

        assertEquals(oid1, oid1);
        assertNotEquals(oid1, oid2);
        assertNotEquals(oid1, "1.3.6.1.4.1.42.2.17");

        System.out.println("Tests passed.");
    }

    static void assertEquals(ObjectIdentifier oid, Object obj)
            throws Exception {
        if (!oid.equals(obj)) {
            throw new Exception("The ObjectIdentifier " + oid.toString() +
                    " should be equal to the Object " + obj.toString());
        }
    }

    static void assertNotEquals(ObjectIdentifier oid, Object obj)
            throws Exception {
        if (oid.equals(obj)) {
            throw new Exception("The ObjectIdentifier " + oid.toString() +
                    " should not be equal to the Object " + obj.toString());
        }
    }
}

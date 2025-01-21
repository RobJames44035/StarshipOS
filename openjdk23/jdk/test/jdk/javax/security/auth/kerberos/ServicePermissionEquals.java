/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8231365
 * @library /test/lib
 * @summary ServicePermission::equals doesn't comply to the spec
 */

import jdk.test.lib.Asserts;

import javax.security.auth.kerberos.ServicePermission;

public class ServicePermissionEquals {
    public static void main(String[] args) throws Exception {
        ServicePermission p1 = new ServicePermission("user@REALM", "initiate");
        ServicePermission p2 = new ServicePermission("user@REALM", "accept");
        ServicePermission p3 = new ServicePermission("user@REALM", "initiate,accept");

        Asserts.assertNotEquals(p1.hashCode(), p2.hashCode());
        Asserts.assertNotEquals(p1.hashCode(), p3.hashCode());
        Asserts.assertNotEquals(p3.hashCode(), p2.hashCode());

        Asserts.assertFalse(p1.equals(p2));
        Asserts.assertFalse(p1.equals(p3));
        Asserts.assertFalse(p3.equals(p2));
    }
}

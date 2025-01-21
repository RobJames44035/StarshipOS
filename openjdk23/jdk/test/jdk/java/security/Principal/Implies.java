/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/*
 * @test
 * @bug 7019834
 * @summary test default implementation of Principal.implies
 */

import java.security.Principal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.security.auth.Subject;
import javax.security.auth.kerberos.KerberosPrincipal;
import javax.security.auth.x500.X500Principal;

public class Implies {
    public static void main(String[] args) throws Exception {
        X500Principal duke = new X500Principal("CN=Duke");
        // should not throw NullPointerException
        testImplies(duke, (Subject)null, false);

        Set<Principal> principals = new HashSet<>();
        principals.add(duke);
        testImplies(duke, principals, true);

        X500Principal tux = new X500Principal("CN=Tux");
        principals.add(tux);
        testImplies(duke, principals, true);

        principals.add(new KerberosPrincipal("duke@java.com"));
        testImplies(duke, principals, true);

        principals.clear();
        principals.add(tux);
        testImplies(duke, principals, false);

        System.out.println("test passed");
    }

    private static void testImplies(Principal principal,
                                    Set<? extends Principal> principals,
                                    boolean result)
        throws SecurityException
    {
        Subject subject = new Subject(true, principals, Collections.emptySet(),
                                      Collections.emptySet());
        testImplies(principal, subject, result);
    }

    private static void testImplies(Principal principal,
                                    Subject subject, boolean result)
        throws SecurityException
    {
        if (principal.implies(subject) != result) {
            throw new SecurityException("test failed");
        }
    }
}

/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
/*
 * @test
 * @author Gary Ellison
 * @bug 4391927
 * @summary RMI regression tests failing due to new behavior in ProtectionDomain
 */

import java.util.Enumeration;
import java.security.*;

public class CheckWhatYouGet {
    public static void main(String[] args) throws Exception {

        CodeSource codesource =
            new CodeSource(null, (java.security.cert.Certificate[]) null);
        Permissions perms = null;
        ProtectionDomain pd = new ProtectionDomain(codesource, perms);

        // this should return null
        if (pd.getPermissions() != null) {
            System.err.println("TEST FAILED: incorrect Permissions returned");
            throw new RuntimeException("test failed: incorrect Permissions returned");
        }

        perms = new Permissions();
        pd = new ProtectionDomain(codesource, perms);
        PermissionCollection pc = pd.getPermissions();
        Enumeration e = pc.elements();

        if (e.hasMoreElements()) {
            System.err.println("TEST FAILED: incorrect Permissions returned");
            throw new RuntimeException("test failed: incorrect Permissions returned");

        }
    }
}

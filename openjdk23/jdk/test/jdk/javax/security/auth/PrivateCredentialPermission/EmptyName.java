/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

/*
 * @test
 * @bug 4893796
 * @summary     PrivateCredentialPermission constructor throws wrong exception
 */

import javax.security.auth.PrivateCredentialPermission;

public class EmptyName {
    public static void main(String[] args) throws Exception {
        try {
            PrivateCredentialPermission perm =
                        new PrivateCredentialPermission("", "read");
            throw new SecurityException("test 1 failed");
        } catch (IllegalArgumentException iae) {
            // good
        }
        try {
            PrivateCredentialPermission perm =
                        new PrivateCredentialPermission(null, "read");
            throw new SecurityException("test 2 failed");
        } catch (IllegalArgumentException iae) {
            // good
        }
        System.out.println("test passed");
    }
}

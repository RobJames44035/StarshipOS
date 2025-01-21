/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */
/*
 * @test
 * @bug 6976536
 * @summary Solaris JREs do not have the krb5.kdc.bad.policy configured by default.
 * @run main/othervm BadKdcDefaultValue
 */

import java.security.Security;

public class BadKdcDefaultValue {
    public static void main(String[] args) throws Exception {
        if (!"tryLast".equalsIgnoreCase(
                Security.getProperty("krb5.kdc.bad.policy"))) {
            throw new Exception("Default value not correct");
        }
    }
}


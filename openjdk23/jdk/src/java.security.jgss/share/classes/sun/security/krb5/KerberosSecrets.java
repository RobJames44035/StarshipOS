/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.security.krb5;

import java.lang.invoke.MethodHandles;
import javax.security.auth.kerberos.KeyTab;

public class KerberosSecrets {
    private static JavaxSecurityAuthKerberosAccess javaxSecurityAuthKerberosAccess;

    public static void setJavaxSecurityAuthKerberosAccess
            (JavaxSecurityAuthKerberosAccess jsaka) {
        javaxSecurityAuthKerberosAccess = jsaka;
    }

    public static JavaxSecurityAuthKerberosAccess
            getJavaxSecurityAuthKerberosAccess() {
        if (javaxSecurityAuthKerberosAccess == null) {
            try {
                MethodHandles.lookup().ensureInitialized(KeyTab.class);
            } catch (IllegalAccessException e) {}
        }
        return javaxSecurityAuthKerberosAccess;
    }
}

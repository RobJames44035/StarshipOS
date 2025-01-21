/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */
package sun.security.jgss.wrapper;

import org.ietf.jgss.*;
import java.lang.ref.Cleaner;

/**
 * This class is a utility class for Kerberos related stuff.
 * @author Valerie Peng
 * @since 1.6
 */
class Krb5Util {
    // A cleaner, shared within this module.
    static final Cleaner cleaner = Cleaner.create();

    // Return the Kerberos TGS principal name using the domain
    // of the specified <code>name</code>
    static String getTGSName(GSSNameElement name)
        throws GSSException {
        String krbPrinc = name.getKrbName();
        int atIndex = krbPrinc.indexOf('@');
        String realm = krbPrinc.substring(atIndex + 1);
        return "krbtgt/" + realm + '@' + realm;
    }
}

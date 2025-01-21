/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package q;

import sun.security.util.HostnameChecker;

public class WithRepl {
   public static void main(String[] argv) throws Exception {
        HostnameChecker hc = HostnameChecker.getInstance(HostnameChecker.TYPE_LDAP);
   }
}

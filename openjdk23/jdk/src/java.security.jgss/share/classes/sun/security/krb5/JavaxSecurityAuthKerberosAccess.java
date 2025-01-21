/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

package sun.security.krb5;

import javax.security.auth.kerberos.KerberosPrincipal;
import javax.security.auth.kerberos.KerberosTicket;
import javax.security.auth.kerberos.KeyTab;

/**
 * An unsafe tunnel to get non-public access to classes in the
 * javax.security.auth.kerberos package.
 */
public interface JavaxSecurityAuthKerberosAccess {
    /**
     * Returns a snapshot to the backing keytab
     */
    public sun.security.krb5.internal.ktab.KeyTab keyTabTakeSnapshot(
            KeyTab ktab);

    public KerberosPrincipal kerberosTicketGetClientAlias(KerberosTicket t);

    public void kerberosTicketSetClientAlias(KerberosTicket t, KerberosPrincipal a);

    public KerberosPrincipal kerberosTicketGetServerAlias(KerberosTicket t);

    public void kerberosTicketSetServerAlias(KerberosTicket t, KerberosPrincipal a);
    /**
     * Returns the proxy for a KerberosTicket.
     */
    public KerberosTicket kerberosTicketGetProxy(KerberosTicket t);

    /**
     * Sets the proxy for a KerberosTicket.
     */
    public void kerberosTicketSetProxy(KerberosTicket t, KerberosTicket p);
}

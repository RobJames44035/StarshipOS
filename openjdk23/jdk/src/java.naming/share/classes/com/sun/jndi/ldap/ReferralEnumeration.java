/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

package com.sun.jndi.ldap;

import javax.naming.NamingEnumeration;

interface ReferralEnumeration<T> extends NamingEnumeration<T> {
    void appendUnprocessedReferrals(LdapReferralException ex);
}

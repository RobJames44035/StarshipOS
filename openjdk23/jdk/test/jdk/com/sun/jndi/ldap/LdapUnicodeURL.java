/*
 * StarshipOS Copyright (c) 2012-2025. R.A. James
 */

/**
 * @test
 * @bug 6961765
 * @summary Double byte characters corrupted in DN for LDAP referrals
 * @modules java.naming/com.sun.jndi.ldap
 */

import com.sun.jndi.ldap.LdapURL;

public class LdapUnicodeURL {
    public static void main(String[] args) throws Exception {
        // First 3 characters of the CJK Unified Ideographs
        String uid = "uid=\u4e00\u4e01\u4e02";
        LdapURL ldURL = new LdapURL("ldap://www.example.com/" + uid);
        if (!ldURL.getDN().equals(uid)) {
            throw new Exception("uid changed to " + ldURL.getDN());
        }
    }
}

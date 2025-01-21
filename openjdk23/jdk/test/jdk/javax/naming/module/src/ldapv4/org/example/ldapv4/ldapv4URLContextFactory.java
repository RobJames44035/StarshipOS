/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * This is a dumy URL context factory for 'ldapv4://'.
 */

package org.example.ldapv4;

import java.util.*;
import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.spi.*;

public class ldapv4URLContextFactory implements ObjectFactory {

    public ldapv4URLContextFactory() {
    }

    public Object getObjectInstance(Object urlInfo, Name name, Context nameCtx,
            Hashtable<?,?> env) throws Exception {

        Hashtable<String,String> env2 = new Hashtable<>();
        env2.put(Context.INITIAL_CONTEXT_FACTORY,
            "com.sun.jndi.ldap.LdapCtxFactory");
        String ldapUrl = (String)env.get(Context.PROVIDER_URL);
        env2.put(Context.PROVIDER_URL, ldapUrl.replaceFirst("ldapv4", "ldap"));
        //env2.put("com.sun.jndi.ldap.trace.ber", System.out);
        return new ldapv4URLContext(env2);
    }
}

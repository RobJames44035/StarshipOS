/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * This class implements the LDAPv3 Authorization Identity response control
 * as defined in
 * <a href="https://tools.ietf.org/html/rfc3829">RFC 3829</a>.
 */

package org.example.authz;

import java.io.*;
import javax.naming.ldap.*;

public class AuthzIdResponseControl extends BasicControl {

    public static final String OID = "2.16.840.1.113730.3.4.15";

    private String identity = null;

    public AuthzIdResponseControl(String id, boolean criticality, byte[] value)
        throws IOException {

        super(id, criticality, value);

        // decode value
        if (value != null && value.length > 0) {
            identity = new String(value, "UTF8");
        }
    }

    public String getIdentity() {
        return identity;
    }
}

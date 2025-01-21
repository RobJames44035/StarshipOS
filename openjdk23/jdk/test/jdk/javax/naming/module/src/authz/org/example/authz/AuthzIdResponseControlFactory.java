/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */

/**
 * This class constructs LDAPv3 Authorization Identity response controls.
 */

package org.example.authz;

import java.io.*;
import javax.naming.*;
import javax.naming.ldap.*;

public class AuthzIdResponseControlFactory extends ControlFactory {

    public AuthzIdResponseControlFactory() {
    }

    public Control getControlInstance(Control control) throws NamingException {
        String id = control.getID();

        try {
            if (id.equals(AuthzIdResponseControl.OID)) {
                return new AuthzIdResponseControl(id, control.isCritical(),
                    control.getEncodedValue());
            }
        } catch (IOException e) {
            NamingException ne = new NamingException();
            ne.setRootCause(e);
            throw ne;
        }

        return null;
    }
}

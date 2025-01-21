/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package jdk.internal.access;

import javax.security.auth.x500.X500Principal;
import sun.security.x509.X500Name;

public interface JavaxSecurityAccess {
    X500Name asX500Name(X500Principal p);
    X500Principal asX500Principal(X500Name n);
}

/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package com.sun.security.jgss;

import sun.security.jgss.GSSCredentialImpl;

class ExtendedGSSCredentialImpl extends GSSCredentialImpl
        implements ExtendedGSSCredential {

    public ExtendedGSSCredentialImpl(GSSCredentialImpl old) {
        super(old);
    }
}

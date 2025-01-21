/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

package com.sun.security.jgss;

import org.ietf.jgss.*;
import sun.security.jgss.GSSContextImpl;
import sun.security.krb5.internal.AuthorizationData;

// The impl is almost identical to GSSContextImpl with only 2 differences:
// 1. It implements the extended interface
// 2. It translates result to data types here in inquireSecContext
class ExtendedGSSContextImpl extends GSSContextImpl
        implements ExtendedGSSContext {

    public ExtendedGSSContextImpl(GSSContextImpl old) {
        super(old);
    }

    @Override
    public Object inquireSecContext(InquireType type) throws GSSException {
        Object output = super.inquireSecContext(type.name());
        if (output != null) {
            if (type == InquireType.KRB5_GET_AUTHZ_DATA) {
                AuthorizationData ad = (AuthorizationData) output;
                AuthorizationDataEntry[] authzData =
                        new AuthorizationDataEntry[ad.count()];
                for (int i = 0; i < ad.count(); i++) {
                    authzData[i] = new AuthorizationDataEntry(
                            ad.item(i).adType, ad.item(i).adData);
                }
                output = authzData;
            }
        }
        return output;
    }
}

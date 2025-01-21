/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package sun.security.krb5.internal;

import sun.security.krb5.*;
import sun.security.util.*;
import java.io.IOException;

public class TGSReq extends KDCReq {

    public TGSReq(PAData[] new_pAData, KDCReqBody new_reqBody) throws IOException {
        super(new_pAData, new_reqBody, Krb5.KRB_TGS_REQ);
    }

    public TGSReq(byte[] data) throws Asn1Exception,
    IOException, KrbException {
        init(new DerValue(data));
    }

    public TGSReq(DerValue encoding) throws Asn1Exception,
    IOException, KrbException {
        init(encoding);
    }

    private void init(DerValue encoding) throws Asn1Exception,
    IOException, KrbException {
        init(encoding, Krb5.KRB_TGS_REQ);
    }

}

/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package sun.security.krb5.internal;

import sun.security.krb5.*;
import sun.security.util.*;
import java.io.IOException;

public class EncTGSRepPart extends EncKDCRepPart {

    public EncTGSRepPart(
            EncryptionKey new_key,
            LastReq new_lastReq,
            int new_nonce,
            KerberosTime new_keyExpiration,
            TicketFlags new_flags,
            KerberosTime new_authtime,
            KerberosTime new_starttime,
            KerberosTime new_endtime,
            KerberosTime new_renewTill,
            PrincipalName new_sname,
            HostAddresses new_caddr,
            PAData[] new_pAData) {
        super(
                new_key,
                new_lastReq,
                new_nonce,
                new_keyExpiration,
                new_flags,
                new_authtime,
                new_starttime,
                new_endtime,
                new_renewTill,
                new_sname,
                new_caddr,
                new_pAData,
                Krb5.KRB_ENC_TGS_REP_PART);
    }

    public EncTGSRepPart(byte[] data) throws Asn1Exception,
            IOException, KrbException {
        init(new DerValue(data));
    }

    public EncTGSRepPart(DerValue encoding) throws Asn1Exception,
            IOException, KrbException {
        init(encoding);
    }

    private void init(DerValue encoding) throws Asn1Exception,
            IOException, KrbException {
        init(encoding, Krb5.KRB_ENC_TGS_REP_PART);
    }

    public byte[] asn1Encode() throws Asn1Exception,
            IOException {
        return asn1Encode(Krb5.KRB_ENC_TGS_REP_PART);
    }
}

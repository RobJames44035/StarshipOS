/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

package sun.security.krb5;

/**
 * Parent class for KrbAsReq and KrbTgsReq.
 */
abstract class KrbKdcReq {

    protected byte[] obuf;

    public byte[] encoding() {
        return obuf;
    }
}

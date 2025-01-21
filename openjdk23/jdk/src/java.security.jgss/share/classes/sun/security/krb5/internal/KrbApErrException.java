/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package sun.security.krb5.internal;

public class KrbApErrException extends sun.security.krb5.KrbException {

    private static final long serialVersionUID = 7545264413323118315L;

    public KrbApErrException(int i) {
        super(i);
    }

    public KrbApErrException(int i, String s) {
        super(i, s);
    }

}

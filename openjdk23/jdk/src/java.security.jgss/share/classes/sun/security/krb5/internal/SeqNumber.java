/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package sun.security.krb5.internal;
import sun.security.krb5.KrbCryptoException;

public interface SeqNumber {
    public void randInit();
    public void init(int start);
    public int current();
    public int next();
    public int step();
}

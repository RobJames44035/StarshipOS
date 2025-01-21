/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

package java.lang;

public final class JdkInternalAbuseOfVbc {

    public JdkInternalAbuseOfVbc() {}

    void abuseVbc(SomeVbc vbc) {

        synchronized(this) {           // OK
            synchronized (vbc) {       // WARN
            }
        }
    }
}


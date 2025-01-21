/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */

public final class ExternalAbuseOfVbc {

    final Integer val = Integer.valueOf(42);
    final String ref = "String";

    void abuseVbc() {
        synchronized(ref) {      // OK
            synchronized (val) { // WARN
            }
        }
    }
}


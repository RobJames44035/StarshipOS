/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.security.jca;

/**
 * Simple class encapsulating a service type and algorithm for lookup.
 * Put in a separate file rather than nested to allow import via ...jca.*.
 *
 * @author  Andreas Sterbenz
 * @since   1.5
 */
public final class ServiceId {

    public final String type;
    public final String algorithm;

    public ServiceId(String type, String algorithm) {
        this.type = type;
        this.algorithm = algorithm;
    }

}

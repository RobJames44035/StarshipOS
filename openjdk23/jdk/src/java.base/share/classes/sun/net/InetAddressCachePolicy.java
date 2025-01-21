/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

package sun.net;

import java.security.Security;

public final class InetAddressCachePolicy {

    // Controls the cache policy for successful lookups only
    private static final String cachePolicyProp = "networkaddress.cache.ttl";
    private static final String cachePolicyPropFallback =
        "sun.net.inetaddr.ttl";

    // Controls the cache stale policy for successful lookups only
    private static final String cacheStalePolicyProp =
        "networkaddress.cache.stale.ttl";
    private static final String cacheStalePolicyPropFallback =
        "sun.net.inetaddr.stale.ttl";

    // Controls the cache policy for negative lookups only
    private static final String negativeCachePolicyProp =
        "networkaddress.cache.negative.ttl";
    private static final String negativeCachePolicyPropFallback =
        "sun.net.inetaddr.negative.ttl";

    public static final int FOREVER = -1;
    public static final int NEVER = 0;

    /* default value for positive lookups */
    public static final int DEFAULT_POSITIVE = 30;

    /* The Java-level namelookup cache policy for successful lookups:
     *
     * -1: caching forever
     * any positive value: the number of seconds to cache an address for
     *
     * default value is 30 seconds
     */
    private static volatile int cachePolicy = DEFAULT_POSITIVE;

    /* The Java-level namelookup cache stale policy:
     *
     * any positive value: the number of seconds to use the stale names
     * zero: do not use stale names
     *
     * default value is never (NEVER).
     */
    private static volatile int staleCachePolicy = NEVER;

    /* The Java-level namelookup cache policy for negative lookups:
     *
     * -1: caching forever
     * any positive value: the number of seconds to cache an address for
     *
     * default value is 0. It can be set to some other value for
     * performance reasons.
     */
    private static volatile int negativeCachePolicy = NEVER;

    /*
     * Initialize
     */
    static {
        /* If the cache policy property is not specified
         *  then the default positive cache value is used.
         */
        Integer tmp = getProperty(cachePolicyProp, cachePolicyPropFallback);
        if (tmp != null) {
            cachePolicy = tmp < 0 ? FOREVER : tmp;
        }
        tmp = getProperty(negativeCachePolicyProp,
                          negativeCachePolicyPropFallback);

        if (tmp != null) {
            negativeCachePolicy = tmp < 0 ? FOREVER : tmp;
        }
        if (cachePolicy > 0) {
            tmp = getProperty(cacheStalePolicyProp,
                              cacheStalePolicyPropFallback);
            if (tmp != null) {
                staleCachePolicy = tmp;
            }
        }
    }

    private static Integer getProperty(String cachePolicyProp,
                                       String cachePolicyPropFallback) {
        try {
            String tmpString = Security.getProperty(cachePolicyProp);
            if (tmpString != null) {
                return Integer.valueOf(tmpString);
            }
        } catch (NumberFormatException ignored) {
            // Ignore
        }

        try {
            String tmpString = System.getProperty(cachePolicyPropFallback);
            if (tmpString != null) {
                return Integer.decode(tmpString);
            }
        } catch (NumberFormatException ignored) {
            // Ignore
        }
        return null;
    }

    public static int get() {
        return cachePolicy;
    }

    public static int getStale() {
        return staleCachePolicy;
    }

    public static int getNegative() {
        return negativeCachePolicy;
    }
}

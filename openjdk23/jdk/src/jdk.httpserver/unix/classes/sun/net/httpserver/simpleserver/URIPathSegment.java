/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package sun.net.httpserver.simpleserver;

/**
 * A class that represents a URI path segment.
 */
final class URIPathSegment {

    private URIPathSegment() { throw new AssertionError(); }

    /**
     * Checks if the segment of a URI path is supported.
     *
     * @param segment the segment string
     * @return true
     */
    static boolean isSupported(String segment) {
        return true;
    }
}

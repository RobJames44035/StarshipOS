/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package jdk.internal.loader;

import java.io.IOException;
import java.net.URL;
import java.io.File;

import sun.net.www.ParseUtil;

/**
 * (Solaris) platform specific handling for file: URLs .
 * urls must not contain a hostname in the authority field
 * other than "localhost".
 *
 * This implementation could be updated to map such URLs
 * on to /net/host/...
 *
 * @author      Michael McMahon
 */

final class FileURLMapper {

    private final URL url;
    private String path;

    FileURLMapper(URL url) {
        this.url = url;
    }

    /**
     * @return the platform specific path corresponding to the URL
     *  so long as the URL does not contain a hostname in the authority field.
     */
    String getPath() throws IOException {
        if (path != null) {
            return path;
        }
        String host = url.getHost();
        if (host == null || host.isEmpty() || "localhost".equalsIgnoreCase(host)) {
            path = url.getFile();
            try {
                path = ParseUtil.decode(path);
            } catch (IllegalArgumentException iae) {
                throw new IOException(iae);
            }
        }
        return path;
    }

    /**
     * Checks whether the file identified by the URL exists.
     */
    boolean exists() throws IOException {
        String s = getPath();
        if (s == null) {
            return false;
        } else {
            File f = new File(s);
            return f.exists();
        }
    }
}

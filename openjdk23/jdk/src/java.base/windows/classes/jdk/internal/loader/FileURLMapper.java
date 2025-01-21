/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package jdk.internal.loader;

import java.io.IOException;
import java.net.URL;
import java.io.File;

import sun.net.www.ParseUtil;

/**
 * (Windows) Platform specific handling for file: URLs . In particular deals
 * with network paths mapping them to UNCs.
 *
 * @author      Michael McMahon
 */

final class FileURLMapper {

    private final URL url;
    private String file;

    FileURLMapper (URL url) {
        this.url = url;
    }

    /**
     * @return the platform specific path corresponding to the URL, and in particular
     *  returns a UNC when the authority contains a hostname
     */
    String getPath() throws IOException {
        if (file != null) {
            return file;
        }
        String host = url.getHost();
        if (host != null && !host.isEmpty() &&
            !"localhost".equalsIgnoreCase(host)) {
            String rest = url.getFile();
            String s = host + ParseUtil.decode (url.getFile());
            file = "\\\\"+ s.replace('/', '\\');
            return file;
        }
        String path = url.getFile().replace('/', '\\');
        try {
            file = ParseUtil.decode(path);
        } catch (IllegalArgumentException iae) {
            throw new IOException(iae);
        }
        return file;
    }

    boolean exists() throws IOException {
        String path = getPath();
        File f = new File(path);
        return f.exists();
    }
}

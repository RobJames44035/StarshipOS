/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

package sun.nio.ch;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import sun.net.NetProperties;
import jdk.internal.util.StaticProperty;

class UnixDomainSocketsUtil {
    private UnixDomainSocketsUtil() { }

    static Charset getCharset() {
        return StandardCharsets.UTF_8;
    }

    /**
     * Return the temp directory for storing automatically bound
     * server sockets.
     *
     * On Windows we search the following directories in sequence:
     *
     * 1. ${jdk.net.unixdomain.tmpdir} if set as system property
     * 2. ${jdk.net.unixdomain.tmpdir} if set as net property
     * 3. %TEMP%
     * 4. ${java.io.tmpdir}
     */
    static String getTempDir() {
        String s = NetProperties.get("jdk.net.unixdomain.tmpdir");
        if (s != null) {
            return s;
        }
        String temp = System.getenv("TEMP");
        if (temp != null) {
            return temp;
        }
        return StaticProperty.javaIoTmpDir();
    }
}


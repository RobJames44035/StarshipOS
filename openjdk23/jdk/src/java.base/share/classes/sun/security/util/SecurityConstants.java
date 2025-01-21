/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */

package sun.security.util;

import java.security.AllPermission;

/**
 * Permission constants and string constants used to create permissions
 * used throughout the JDK.
 */
public final class SecurityConstants {
    // Cannot create one of these
    private SecurityConstants () {
    }

    // Commonly used string constants for permission actions used by
    // SecurityManager. Declare here for shortcut when checking permissions
    // in FilePermission, SocketPermission, and PropertyPermission.

    public static final String FILE_DELETE_ACTION = "delete";
    public static final String FILE_EXECUTE_ACTION = "execute";
    public static final String FILE_READ_ACTION = "read";
    public static final String FILE_WRITE_ACTION = "write";
    public static final String FILE_READLINK_ACTION = "readlink";

    public static final String SOCKET_RESOLVE_ACTION = "resolve";
    public static final String SOCKET_CONNECT_ACTION = "connect";
    public static final String SOCKET_LISTEN_ACTION = "listen";
    public static final String SOCKET_ACCEPT_ACTION = "accept";
    public static final String SOCKET_CONNECT_ACCEPT_ACTION = "connect,accept";

    public static final String PROPERTY_RW_ACTION = "read,write";
    public static final String PROPERTY_READ_ACTION = "read";
    public static final String PROPERTY_WRITE_ACTION = "write";

    // Permission constants used in the various checkPermission() calls in JDK.

    // java.net.URLConnection, java.security.AllPermission
    public static final AllPermission ALL_PERMISSION = new AllPermission();

    public static final String PROVIDER_VER =
        System.getProperty("java.specification.version");
}

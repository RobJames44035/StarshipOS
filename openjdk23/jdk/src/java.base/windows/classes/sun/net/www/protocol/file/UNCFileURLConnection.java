/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

package sun.net.www.protocol.file;

import java.io.File;
import java.io.FilePermission;
import java.net.URL;
import java.security.Permission;

final class UNCFileURLConnection extends FileURLConnection {

    private final String effectivePath;
    private volatile Permission permission;

    UNCFileURLConnection(URL u, File file, String effectivePath) {
        super(u, file);
        this.effectivePath = effectivePath;
    }

    @Override
    public Permission getPermission() {
        Permission perm = permission;
        if (perm == null) {
            permission = perm = new FilePermission(effectivePath, "read");
        }
        return perm;
    }
}


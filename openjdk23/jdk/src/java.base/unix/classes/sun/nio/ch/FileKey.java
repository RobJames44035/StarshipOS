/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;

/*
 * Represents a key to a specific file on Solaris or Linux
 */
public class FileKey {

    private final long st_dev;    // ID of device
    private final long st_ino;    // Inode number

    private FileKey(long st_dev, long st_ino) {
        this.st_dev = st_dev;
        this.st_ino = st_ino;
    }

    public static FileKey create(FileDescriptor fd) throws IOException {
        long finfo[] = new long[2];
        init(fd, finfo);
        return new FileKey(finfo[0], finfo[1]);
    }

    @Override
    public int hashCode() {
        return (int)(st_dev ^ (st_dev >>> 32)) +
               (int)(st_ino ^ (st_ino >>> 32));
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        return obj instanceof FileKey other
                && (this.st_dev == other.st_dev)
                && (this.st_ino == other.st_ino);
    }

    private static native void init(FileDescriptor fd, long[] finfo)
        throws IOException;

    static {
        IOUtil.load();
    }
}

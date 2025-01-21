/*
 * StarshipOS Copyright (c) 2005-2025. R.A. James
 */

package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;

/*
 * Represents a key to a specific file on Windows
 */
public class FileKey {

    private final int dwVolumeSerialNumber;
    private final int nFileIndexHigh;
    private final int nFileIndexLow;

    private FileKey(int dwVolumeSerialNumber, int nFileIndexHigh,
        int nFileIndexLow) {
        this.dwVolumeSerialNumber = dwVolumeSerialNumber;
        this.nFileIndexHigh = nFileIndexHigh;
        this.nFileIndexLow = nFileIndexLow;
    }

    public static FileKey create(FileDescriptor fd) throws IOException {
        int finfo[] = new int[3];
        init(fd, finfo);
        return new FileKey(finfo[0], finfo[1], finfo[2]);
    }

    @Override
    public int hashCode() {
        return dwVolumeSerialNumber + nFileIndexHigh + nFileIndexLow;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        return obj instanceof FileKey other
                && this.dwVolumeSerialNumber == other.dwVolumeSerialNumber
                && this.nFileIndexHigh == other.nFileIndexHigh
                && this.nFileIndexLow == other.nFileIndexLow;
    }

    private static native void init(FileDescriptor fd, int[] finfo)
        throws IOException;

    static {
        IOUtil.load();
    }
}

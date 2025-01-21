/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;

public class FileDispatcherImpl extends UnixFileDispatcherImpl {
    FileDispatcherImpl() {
        super();
    }

    int force(FileDescriptor fd, boolean metaData) throws IOException {
        return force0(fd, metaData);
    }

    protected long transferTo(FileDescriptor src, long position,
                              long count, FileDescriptor dst,
                              boolean append)
    {
        return transferTo0(src, position, count, dst, append);
    }

    // --- native methods ---

    static native int force0(FileDescriptor fd, boolean metaData)
        throws IOException;

    private static native long transferTo0(FileDescriptor src, long position,
                                           long count, FileDescriptor dst,
                                           boolean append);
}

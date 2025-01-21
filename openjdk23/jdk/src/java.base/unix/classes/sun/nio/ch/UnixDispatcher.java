/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

package sun.nio.ch;

import java.io.FileDescriptor;
import java.io.IOException;

abstract class UnixDispatcher extends NativeDispatcher {

    void close(FileDescriptor fd) throws IOException {
        close0(fd);
    }

    void preClose(FileDescriptor fd) throws IOException {
        preClose0(fd);
    }

    static native void close0(FileDescriptor fd) throws IOException;

    static native void preClose0(FileDescriptor fd) throws IOException;

    static native void init();

    static {
        IOUtil.load();
        init();
    }
}

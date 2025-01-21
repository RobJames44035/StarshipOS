/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package sun.nio.fs;

import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;

/**
 * Bsd implementation of FileSystemProvider
 */

class BsdFileSystemProvider extends UnixFileSystemProvider {
    public BsdFileSystemProvider() {
        super();
    }

    @Override
    BsdFileSystem newFileSystem(String dir) {
        return new BsdFileSystem(this, dir);
    }

    @Override
    BsdFileStore getFileStore(UnixPath path) throws IOException {
        return new BsdFileStore(path);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <V extends FileAttributeView> V getFileAttributeView(Path obj,
                                                                Class<V> type,
                                                                LinkOption... options)
    {
        if (type == BasicFileAttributeView.class ||
            type == PosixFileAttributeView.class ||
            type == UserDefinedFileAttributeView.class) {

            UnixPath file = UnixPath.toUnixPath(obj);
            boolean followLinks = Util.followLinks(options);

            if (type == BasicFileAttributeView.class)
                return (V) BsdFileAttributeViews.createBasicView(file,
                                                                 followLinks);
            else if (type == PosixFileAttributeView.class)
                return (V) BsdFileAttributeViews.createPosixView(file,
                                                                 followLinks);
            // user-defined is the only possibility
            return (V) new BsdUserDefinedFileAttributeView(file, followLinks);
        }

        return super.getFileAttributeView(obj, type, options);
    }

    @Override
    public DynamicFileAttributeView getFileAttributeView(Path obj,
                                                         String name,
                                                         LinkOption... options)
    {
        if (name.equals("basic") || name.equals("posix") ||
            name.equals("unix")  || name.equals("user")) {

            UnixPath file = UnixPath.toUnixPath(obj);
            boolean followLinks = Util.followLinks(options);

            if (name.equals("basic"))
                return BsdFileAttributeViews.createBasicView(file, followLinks);

            if (name.equals("posix"))
                return BsdFileAttributeViews.createPosixView(file, followLinks);

            if (name.equals("unix"))
                return BsdFileAttributeViews.createUnixView(file, followLinks);

            // user-defined is the only possibility
            return new BsdUserDefinedFileAttributeView(file, followLinks);
        }

        return super.getFileAttributeView(obj, name, options);
    }
}

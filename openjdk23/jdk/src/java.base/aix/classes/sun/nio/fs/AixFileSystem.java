/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

package sun.nio.fs;

import java.nio.file.*;
import java.nio.file.attribute.*;
import java.io.IOException;
import java.util.*;
import static sun.nio.fs.AixNativeDispatcher.*;

/**
 * AIX implementation of FileSystem
 */

class AixFileSystem extends UnixFileSystem {

    AixFileSystem(UnixFileSystemProvider provider, String dir) {
        super(provider, dir);
    }

    @Override
    public WatchService newWatchService()
        throws IOException
    {
        return new PollingWatchService();
    }

    // lazy initialization of the list of supported attribute views
    private static class SupportedFileFileAttributeViewsHolder {
        static final Set<String> supportedFileAttributeViews =
            supportedFileAttributeViews();
        private static Set<String> supportedFileAttributeViews() {
            Set<String> result = new HashSet<String>();
            result.addAll(UnixFileSystem.standardFileAttributeViews());
            return Collections.unmodifiableSet(result);
        }
    }

    @Override
    public Set<String> supportedFileAttributeViews() {
        return SupportedFileFileAttributeViewsHolder.supportedFileAttributeViews;
    }

    @Override
    void copyNonPosixAttributes(int ofd, int nfd) {
        // TODO: Implement if needed.
    }

    /**
     * Returns object to iterate over the mount entries returned by mntctl
     */
    @Override
    Iterable<UnixMountEntry> getMountEntries() {
        UnixMountEntry[] entries = null;
        try {
            entries = getmntctl();
        } catch (UnixException x) {
            // nothing we can do
        }
        if (entries == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(entries);
    }

    @Override
    FileStore getFileStore(UnixMountEntry entry) throws IOException {
        return new AixFileStore(this, entry);
    }
}

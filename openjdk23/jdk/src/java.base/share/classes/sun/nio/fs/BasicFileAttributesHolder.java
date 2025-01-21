/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package sun.nio.fs;

import java.nio.file.attribute.BasicFileAttributes;

/**
 * Implemented by objects that may hold or cache the attributes of a file.
 */

public interface BasicFileAttributesHolder {
    /**
     * Returns cached attributes (may be null). If file is a symbolic link then
     * the attributes are the link attributes and not the final target of the
     * file.
     */
    BasicFileAttributes get();

    /**
     * Invalidates cached attributes
     */
    void invalidate();
}

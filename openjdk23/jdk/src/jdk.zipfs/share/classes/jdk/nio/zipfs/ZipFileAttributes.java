/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

package jdk.nio.zipfs;

import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.PosixFilePermission;
import java.util.Optional;
import java.util.Set;

/**
 * The attributes of a file stored in a zip file.
 *
 * @author Xueming Shen, Rajendra Gutupalli, Jaya Hangal
 */
interface ZipFileAttributes extends BasicFileAttributes {
    long compressedSize();
    long crc();
    int method();
    byte[] extra();
    byte[] comment();
    Optional<Set<PosixFilePermission>> storedPermissions();
}

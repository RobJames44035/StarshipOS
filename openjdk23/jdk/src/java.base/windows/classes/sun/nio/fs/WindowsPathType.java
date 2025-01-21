/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

package sun.nio.fs;

/**
 * A type safe enum of Windows path types.
 */

enum WindowsPathType {
    ABSOLUTE,                   //  C:\foo
    UNC,                        //  \\server\share\foo
    RELATIVE,                   //  foo
    DIRECTORY_RELATIVE,         //  \foo
    DRIVE_RELATIVE              //  C:foo
}

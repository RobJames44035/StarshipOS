/*
 * StarshipOS Copyright (c) 2013-2025. R.A. James
 */

/* @test
   @bug 4035924 4095767
   @summary General exhaustive test of solaris pathname handling
   @author Mark Reinhold

   @build General GeneralSolaris
   @run main GeneralSolaris
 */

import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.file.attribute.*;

public class GeneralSolaris extends General {

    private static void checkUnreadable() throws Exception {
        Path file = Paths.get(baseDir, "unreadableFile");
        Path dir = Paths.get(baseDir, "unreadableDir");
        Set<PosixFilePermission> perms = PosixFilePermissions.fromString("---------");
        FileAttribute<Set<PosixFilePermission>> attr = PosixFilePermissions.asFileAttribute(perms);
        Files.createFile(file, attr);
        Files.createDirectory(dir, attr);

        String unreadableFile = file.toString();
        String unreadableDir = dir.toString();

        checkSlashes(2, false, unreadableDir, unreadableDir);
        checkSlashes(2, false, unreadableFile, unreadableFile);

        Files.delete(file);
        Files.delete(dir);
    }

    private static void checkPaths() throws Exception {
        // Make sure that an empty relative path is tested
        checkNames(1, true, userDir + File.separator, "");
        checkNames(3, true, baseDir + File.separator,
                   relative + File.separator);

        checkSlashes(2, true, baseDir, baseDir);
    }

    public static void main(String[] args) throws Exception {
        if (File.separatorChar != '/') {
            /* This test is only valid on Unix systems */
            return;
        }
        if (args.length > 0) debug = true;

        initTestData(3);
        checkUnreadable();
        checkPaths();
    }
}

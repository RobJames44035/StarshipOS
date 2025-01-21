/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

package jdk.test.lib.jittester;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import jdk.test.lib.util.FileUtils;

/**
  * A temporary directory wrapper.
  * Makes sure that the directory gets deleted after usage.
  */
public class TempDir {
    public final Path path;

    /**
     * Creates a temporary directory with a given suffix.
     * The directory is deep deleted on VM shutdown using a ShutdownHook.
     */
    public TempDir(String suffix) {
        try {
            path = Files.createTempDirectory(suffix).toAbsolutePath();
            Runtime.getRuntime().addShutdownHook(new Thread(this::delete));
        } catch (IOException e) {
            throw new Error("Can't create a tmp dir for " + suffix, e);
        }

        System.out.println("DBG: Temp folder created: '" + path + "'");
    }

    private void delete() {
        try {
            FileUtils.deleteFileTreeWithRetry(path);
            System.out.println("DBG: Temp folder deleted: '" + path + "'");
        } catch (IOException exc) {
            throw new Error("Could not deep delete '" + path + "'", exc);
        }
    }
}

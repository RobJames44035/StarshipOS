/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.file.StandardOpenOption.*;

/**
 * Common library for various test file utility functions.
 */
public final class FileChannelUtils {

    public static Path createSparseTempFile(String prefix, String suffix) throws IOException {
        Path file = Files.createTempFile(prefix, suffix);
        Files.delete(file); // need CREATE_NEW to make the file sparse

        FileChannel fc = FileChannel.open(file, CREATE_NEW, SPARSE, WRITE);
        fc.close();
        return file;
    }
}

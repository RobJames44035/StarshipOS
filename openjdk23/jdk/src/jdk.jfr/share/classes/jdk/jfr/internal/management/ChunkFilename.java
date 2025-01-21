/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */
package jdk.jfr.internal.management;

import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.io.IOException;

import jdk.jfr.internal.util.ValueFormatter;

// Allows a remote streaming client to create chunk files
// with same naming scheme as the JVM.
public final class ChunkFilename {
   private static final int MAX_CHUNK_NAMES = 100_000;
   private static final String FILE_EXTENSION = ".jfr";

   private final Path directory;

   private Path lastPath;
   private int counter;

   public ChunkFilename(Path directory) {
       this.directory = directory;
   }

   public String next(LocalDateTime time) throws IOException {
       String filename = ValueFormatter.formatDateTime(time);
       Path p = directory.resolve(filename + FILE_EXTENSION);

       // If less than one file per second (typically case)
       if (lastPath == null || !p.equals(lastPath)) {
           if (!Files.exists(p)) {
               counter = 1; // reset counter
               lastPath = p;
               return p.toString();
           }
       }

       // If more than one file per second
       while (counter < MAX_CHUNK_NAMES) {
           String extendedName = makeExtendedName(filename, counter);
           p = directory.resolve(extendedName);
           counter++;
           if (!Files.exists(p)) {
               return p.toString();
           }
       }
       throw new IOException("Unable to find unused filename after " + counter + " attempts");
   }

   private String makeExtendedName(String filename, int counter) {
       StringBuilder sb = new StringBuilder();
       sb.append(filename);
       sb.append('_');
       if (counter < 10) { // chronological sorted
           sb.append('0');
       }
       sb.append(counter);
       sb.append(FILE_EXTENSION);
       return sb.toString();
   }
}

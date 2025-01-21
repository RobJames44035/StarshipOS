/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/* @test
 * @bug 6808647
 * @summary Checks that a DirectoryStream's iterator returns the expected
 *    path when opening a directory by specifying only the drive letter.
 * @library ..
 */

import java.nio.file.*;
import java.io.File;
import java.io.IOException;

public class DriveLetter {

    public static void main(String[] args) throws IOException {
        String os = System.getProperty("os.name");
        if (!os.startsWith("Windows")) {
            System.out.println("This is Windows specific test");
            return;
        }
        String here = System.getProperty("user.dir");
        if (here.length() < 2 || here.charAt(1) != ':')
            throw new RuntimeException("Unable to determine drive letter");

        // create temporary file in current directory
        File tempFile = File.createTempFile("foo", "tmp", new File(here));
        try {
            // we should expect C:foo.tmp to be returned by iterator
            String drive = here.substring(0, 2);
            Path expected = Paths.get(drive).resolve(tempFile.getName());

            boolean found = false;
            Path dir = Paths.get(drive);
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
                for (Path file : stream) {
                    if (file.equals(expected)) {
                        found = true;
                        break;
                    }
                }
            }
            if (!found)
                throw new RuntimeException("Temporary file not found???");

        } finally {
            tempFile.delete();
        }
    }
}

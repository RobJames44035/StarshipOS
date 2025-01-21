/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */

/*
 * @test
 * @bug     7087549
 * @summary Test custom options with newInputStream.
 * @author  Brandon Passanisi
 * @library ..
 * @build   CustomOptions PassThroughFileSystem
 * @run main CustomOptions
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.spi.FileSystemProvider;
import java.nio.channels.SeekableByteChannel;
import java.util.Collections;
import java.util.Set;
import java.util.Map;

public class CustomOptions {

    // Create a custom option
    static enum CustomOption implements OpenOption {
        IGNORE,
    }

    // number of times that IGNORE option is observed
    static int ignoreCount;

    // A pass through provider that supports a custom open option
    static class MyCustomProvider extends PassThroughFileSystem.PassThroughProvider {
        public MyCustomProvider() { }

        @Override
        public SeekableByteChannel newByteChannel(Path path,
                                                  Set<? extends OpenOption> options,
                                                  FileAttribute<?>... attrs)
            throws IOException
        {
            if (options.contains(CustomOption.IGNORE)) {
                ignoreCount++;
                options.remove(CustomOption.IGNORE);
            }
            return super.newByteChannel(path, options, attrs);
        }
    }

    public static void main(String[] args) throws Exception {
        FileSystemProvider provider = new MyCustomProvider();
        Map<String,?> env = Collections.emptyMap();
        URI uri = URI.create("pass:///");
        FileSystem fs = provider.newFileSystem(uri, env);

        // Create temp dir for testing
        Path dir = TestUtil.createTemporaryDirectory();
        try {

            // Create temp file for testing
            Path path = fs.getPath(dir.resolve("foo").toString());
            Files.createFile(path);

            // Test custom option
            Files.newInputStream(path, CustomOption.IGNORE).close();
            if (ignoreCount != 1)
                throw new RuntimeException("IGNORE option not passed through");

            // Test null option
            try {
                Files.newInputStream(path, new OpenOption[] { null }).close();
                throw new RuntimeException("NullPointerException expected");
            } catch (NullPointerException ignore) { }

            // Test unsupported options
            try {
                Files.newInputStream(path, StandardOpenOption.WRITE).close();
                throw new RuntimeException("UnsupportedOperationException expected");
            } catch (UnsupportedOperationException uoe) { }
            try {
                Files.newInputStream(path, StandardOpenOption.APPEND).close();
                throw new RuntimeException("UnsupportedOperationException expected");
            } catch (UnsupportedOperationException uoe) { }

        } finally {
            // Cleanup
            TestUtil.removeAll(dir);
        }
    }
}

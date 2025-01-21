/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

/**
 * @test
 * @bug 8344882
 * @summary Deallocation failure for temporary buffers
 * @run junit/othervm -XX:MaxDirectMemorySize=32768 UnmeteredTempBuffers
 */
import java.io.InputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import static java.nio.file.StandardOpenOption.*;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class UnmeteredTempBuffers {
    @ParameterizedTest
    @ValueSource(ints = {16384, 32768, 32769, 65536})
    void testFileChannel(int cap) throws IOException {
        Path file = Files.createTempFile("prefix", "suffix");
        try (FileChannel ch = FileChannel.open(file, WRITE, DELETE_ON_CLOSE)) {
            ByteBuffer buf = ByteBuffer.wrap(new byte[cap]);
            try {
                ch.write(buf);
            } catch (OutOfMemoryError oome) {
                throw new RuntimeException(oome);
            }
        }  finally {
            Files.deleteIfExists(file);
        }
    }

    @ParameterizedTest
    @ValueSource(ints = {16384, 32768, 32769, 65536})
    void testInputStream(int cap) throws IOException {
        Path file = Files.createTempFile("prefix", "suffix");
        try {
            byte[] bytes = new byte[cap];
            Files.write(file, bytes);
            try (InputStream in = Files.newInputStream(file)) {
                in.read(bytes);
            } catch (OutOfMemoryError oome) {
                throw new RuntimeException(oome);
            }
        }  finally {
            Files.delete(file);
        }
    }
}

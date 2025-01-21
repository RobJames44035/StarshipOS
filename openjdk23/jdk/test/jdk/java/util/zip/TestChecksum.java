/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/**
 * @test @summary Test that default methods in Checksum works as expected
 * @build ChecksumBase
 * @run main TestChecksum
 */
import java.util.zip.CRC32C;
import java.util.zip.Checksum;

public class TestChecksum {

    public static void main(String[] args) {
        ChecksumBase.testAll(new MyCRC32C(), 0xE3069283L);
    }

    /**
     * Only implementing required methods
     */
    private static class MyCRC32C implements Checksum {

        private final CRC32C crc32c = new CRC32C();

        @Override
        public void update(int b) {
            crc32c.update(b);
        }

        @Override
        public void update(byte[] b, int off, int len) {
            crc32c.update(b, off, len);
        }

        @Override
        public long getValue() {
            return crc32c.getValue();
        }

        @Override
        public void reset() {
            crc32c.reset();
        }

    }
}

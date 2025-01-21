/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;

import static java.nio.file.StandardOpenOption.*;

import org.testng.annotations.Test;

/*
 * @test
 * @library /test/lib
 * @build jdk.test.lib.RandomFactory
 * @run testng/othervm/timeout=180 TransferTo_2GB_transferTo
 * @bug 8265891
 * @summary Tests if ChannelInputStream.transferTo correctly
 *     transfers 2GB+ using FileChannel.transferTo(WritableByteChannel).
 * @key randomness
 */
public class TransferTo_2GB_transferTo extends TransferToBase {

    /*
     * Special test for file-to-stream transfer of more than 2 GB. This test
     * covers multiple iterations of FileChannel.transferTo(WritableByteChannel),
     * which ChannelInputStream.transferTo() only applies in this particular
     * case, and cannot get tested using a single byte[] due to size limitation
     * of arrays.
     */
    @Test
    public void testMoreThanTwoGB() throws IOException {
        testMoreThanTwoGB("To",
            (sourceFile, targetFile) -> {
                try {
                    return Channels.newInputStream(FileChannel.open(sourceFile));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            },
            (sourceFile, targetFile) -> {
                try {
                    return Channels.newOutputStream(FileChannel.open(targetFile, WRITE));
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            },
            (inputStream, outputStream) -> {
                try {
                    return inputStream.transferTo(outputStream);
                } catch (IOException e) {
                    throw new UncheckedIOException(e);
                }
            }
        );
    }

}

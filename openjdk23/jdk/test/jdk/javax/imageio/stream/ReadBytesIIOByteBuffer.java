/*
 * StarshipOS Copyright (c) 2004-2025. R.A. James
 */

/*
 * @test
 * @bug 4446906
 * @summary Checks if ImageInputStreamImpl.readBytes(IIOByteBuffer) tests for
 *          len < 0
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import javax.imageio.stream.IIOByteBuffer;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.MemoryCacheImageInputStream;

public class ReadBytesIIOByteBuffer {

    public static void main(String[] argv) {
        byte[] bar = {1, 1, 1};
        InputStream is = new ByteArrayInputStream(bar);

        ImageInputStream iis = new MemoryCacheImageInputStream(is);
        byte[] b = new byte[10];
        IIOByteBuffer iiob = new IIOByteBuffer(b, 0, b.length);
        try {
            iis.readBytes(iiob, -1);
        } catch (IndexOutOfBoundsException e) {
            return;
        } catch (Exception e) {
            throw new RuntimeException("Unexpected exception: " + e);
        }
        throw new RuntimeException("No exception thrown for len < 0!");
    }
}

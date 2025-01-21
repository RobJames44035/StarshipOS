/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/**
 * @test %I% %E%
 * @bug 6954275
 * @summary Check that UnsyncByteArrayOutputStream does not
 *          throw ArrayIndexOutOfBoundsException
 * @modules java.xml.crypto/com.sun.org.apache.xml.internal.security.utils
 * @compile -XDignore.symbol.file BufferOverflowTest.java
 * @run main BufferOverflowTest
 */

import com.sun.org.apache.xml.internal.security.utils.UnsyncByteArrayOutputStream;

public class BufferOverflowTest {

    public static void main(String[] args) throws Exception {
        try {
            UnsyncByteArrayOutputStream out = new UnsyncByteArrayOutputStream();
            out.write(new byte[(8192) << 2 + 1]);
            System.out.println("PASSED");
        } catch (ArrayIndexOutOfBoundsException e) {
            System.err.println("FAILED, got ArrayIndexOutOfBoundsException");
            throw new Exception(e);
        }
    }
}

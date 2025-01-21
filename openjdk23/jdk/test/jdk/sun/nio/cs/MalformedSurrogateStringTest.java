/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 4153987 6354525
 * @summary Malformed surrogates should be handled by the converter in
 * substitution mode.
 */

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MalformedSurrogateStringTest {

    public static void main(String[] args) throws Exception {

        String fe = System.getProperty("file.encoding");
        if (  fe.equalsIgnoreCase("UTF8")
              || fe.equalsIgnoreCase("UTF-8")
              || fe.equalsIgnoreCase("UTF_8"))
            // This test is meaningless if the default charset
            // does handle surrogates
            return;

        System.out.println("Testing string conversion...");
        /* Example with malformed surrogate, and an offset */
        String t = "abc\uD800\uDB00efgh";
        String t2 = t.substring(2);
        byte[] b = t2.getBytes();
        System.err.println(b.length);
        for (int i = 0; i < b.length; i++)
            System.err.println("[" + i + "]" + "=" + (char) b[i]
                               + "=" + (int) b[i]);
        if (b.length != 7) {
            throw new Exception("Bad string conversion for bad surrogate");
        }

        /* Example with a proper surrogate, no offset. Always worked */
        String t3 = "abc\uD800\uDC00efgh";
        byte[] b2 = t3.getBytes();
        System.out.println(b2.length);
        for(int i = 0; i < b2.length; i++)
            System.err.println("[" + i + "]" + "=" + (char) b2[i]);
        if (b2.length != 8) {
            throw new Exception("Bad string conversion for good surrogate");
        }

        OutputStream os = new ByteArrayOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os);
        System.out.println("Testing flush....");
        /* Check for the case where the converter has a left over
           high surrogate when flush is called on the converter */
        osw.flush();
        String s = "abc\uD800"; // High surrogate
        char[] c = s.toCharArray();
        osw.write(s, 0, 4);
        osw.flush();

        System.out.println("Testing convert...");
        /* Verify that all other characters go through */
        for (int k = 1; k < 65535 ; k++) {
            osw.write("Char[" + k + "]=\"" + ((char) k) + "\"");
        }

    }
}

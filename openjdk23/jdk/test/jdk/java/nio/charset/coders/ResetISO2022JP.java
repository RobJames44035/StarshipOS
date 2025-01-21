/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/* @test
 * @bug 6226510
 * @summary Check that ISO-2022-JP's encoder correctly resets to ASCII mode
 * @modules jdk.charsets
 * @author Martin Buchholz
 */

import java.nio.*;
import java.nio.charset.*;

public class ResetISO2022JP {

    public static void main(String[] args) throws Exception {
        if (! (encode(true).equals(encode(false))))
            throw new Exception("Mismatch!");
    }

    static String encode(boolean reuseEncoder) {
        String s = "\u3042\u3043\u3044";

        CharsetEncoder e = Charset.forName("ISO-2022-JP").newEncoder();

        if (reuseEncoder) {
            // I'm turning japanese. Yes I'm turning japanese.  Yes I think so!
            e.encode(CharBuffer.wrap(s), ByteBuffer.allocate(64), true);

            // Should put encoder back into ASCII mode
            e.reset();
        }

        ByteBuffer bb = ByteBuffer.allocate(64);
        e.encode(CharBuffer.wrap(s), bb, true);
        e.flush(bb);
        bb.flip();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bb.limit(); i++)
            sb.append(String.format("%02x ", bb.get(i)));
        System.out.println(sb);
        return sb.toString();
    }
}

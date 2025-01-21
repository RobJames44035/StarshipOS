/*
 * StarshipOS Copyright (c) 2010-2025. R.A. James
 */

/* @test
   @bug 6176819
   @summary Check if COMPUND_TEXT charset works as expected
   @run main/timeout=1200 TestCOMP
 */

import java.util.HashMap;
import java.util.Set;
import java.io.UnsupportedEncodingException;
import java.nio.charset.*;
import java.nio.*;

public class TestCOMP {
    public static void main(String[] argv) throws CharacterCodingException {
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows"))
            return;
        try {
            String src =
                "JIS0208\u4eb0" +
                "ASCII" +
                "JIS0212\u4e74\u4e79" +
                "GB2312\u7279\u5b9a" +
                "JIS0201\uff67\uff68" +
                "Johab\uac00\uac01";

            byte[] ba = src.getBytes("COMPOUND_TEXT");
            /*
            System.out.print("ba=");
            for (int i = 0; i < ba.length; i++) {
                System.out.printf("<%x> ", ba[i] & 0xff);
            }
            System.out.println();
            */
            String dst = new String(ba, "COMPOUND_TEXT");
            char[] ca = dst.toCharArray();
            /*
            System.out.print("ca=");
            for (int i = 0; i < ca.length; i++) {
                System.out.printf("<%x> ", ca[i] & 0xffff);
            }
            System.out.println();
            */
            if (!src.equals(dst)) {
                System.out.printf("src=<%s>\n", src);
                System.out.printf("dst=<%s>\n", dst);
                throw new CharacterCodingException();
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}

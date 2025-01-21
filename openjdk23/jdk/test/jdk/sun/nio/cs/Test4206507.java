/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */

/* @test
   @bug 4206507
   @summary verify that we can obtain and use a converter for encoding
    ISO8859-9 in the Turkish locale.
 */

import java.util.Locale;
import java.io.UnsupportedEncodingException;

public class Test4206507 {
    public static void main(String[] args) throws UnsupportedEncodingException {
        Locale l = Locale.getDefault();
        try {
            Locale.setDefault(Locale.of("tr", "TR"));
            byte[] b = "".getBytes("ISO8859-9");
        } finally {
            Locale.setDefault(l);
        }
    }
}

/*
 * StarshipOS Copyright (c) 2021-2025. R.A. James
 */

/*
 * @test
 * @bug 8264864
 * @summary Multiple byte tag not supported by ASN.1 encoding
 * @modules java.base/sun.security.util
 * @library /test/lib
 */

import jdk.test.lib.Utils;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

import java.io.IOException;

public class WideTag {

    public static void main(String[] args) throws Exception {

        // Small ones
        DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte)30);
        DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte)0);

        // Big ones
        Utils.runAndCheckException(
                () -> DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte)31),
                IllegalArgumentException.class);
        Utils.runAndCheckException(
                () -> DerValue.createTag(DerValue.TAG_CONTEXT, true, (byte)222),
                IllegalArgumentException.class);

        // We don't accept number 31
        Utils.runAndCheckException(() -> new DerValue((byte)0xbf, new byte[10]),
                IllegalArgumentException.class);

        // CONTEXT [98] size 97. Not supported. Should fail.
        // Before this fix, it was interpreted as CONTEXT [31] size 98.
        byte[] wideDER = new byte[100];
        wideDER[0] = (byte)0xBF;
        wideDER[1] = (byte)98;
        wideDER[2] = (byte)97;

        Utils.runAndCheckException(() -> new DerValue(wideDER),
                IOException.class);
        Utils.runAndCheckException(() -> new DerInputStream(wideDER).getDerValue(),
                IOException.class);
    }
}

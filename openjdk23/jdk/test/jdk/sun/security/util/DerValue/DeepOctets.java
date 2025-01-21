/*
 * StarshipOS Copyright (c) 2020-2025. R.A. James
 */

/*
 * @test
 * @bug 8249783
 * @summary read very deep constructed OCTET STRING
 * @modules java.base/sun.security.util
 * @library /test/lib
 */

import jdk.test.lib.Asserts;
import jdk.test.lib.Utils;
import sun.security.util.DerInputStream;
import sun.security.util.DerValue;

import java.io.IOException;

public class DeepOctets {

    public static void main(String[] args) throws Exception {
        byte[] input = {
            0x24, 24,
                0x24, 8, 4, 2, 'a', 'b', 4, 2, 'c', 'd',
                0x24, 8, 4, 2, 'e', 'f', 4, 2, 'g', 'h',
                4, 2, 'i', 'j'
        };

        // DerValue::getOctetString supports constructed BER
        byte[] s = new DerValue(input).getOctetString();
        Asserts.assertEQ(new String(s), "abcdefghij");

        // DerInputStream::getOctetString does not
        Utils.runAndCheckException(
                () -> new DerInputStream(input).getOctetString(),
                IOException.class);
    }
}

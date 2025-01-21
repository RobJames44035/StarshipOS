/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */

/*
 * @test
 * @bug 8151788 8286526
 * @summary NullPointerException from ntlm.Client.type3
 * @modules java.base/com.sun.security.ntlm
 * @run main NULLTargetInfoTest
 */
import com.sun.security.ntlm.Client;

public class NULLTargetInfoTest {

    public static void main(String[] args) throws Exception {
        Client c = new Client(null, "host", "user", "domain", "pass".toCharArray());
        c.type1();
        // this input does have the 0x800000 bit(NTLMSSP_NEGOTIATE_TARGET_INFO) set
        // but after offset 40 all eight bytes are all zero which means there is no
        // security buffer for target info.
        byte[] type2 = hex(
                "4E 54 4C 4D 53 53 50 00 02 00 00 00 00 00 00 00"
                + "00 00 00 00 05 82 89 00 0B 87 81 B6 2D 6E 8B C1"
                + "00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00");
        byte[] nonce = new byte[8];
        c.type3(type2, nonce);
    }

    private static byte[] hex(String str) {
        str = str.replaceAll("\\s", "");
        byte[] response = new byte[str.length() / 2];
        int index = 0;
        for (int i = 0; i < str.length(); i += 2) {
            response[index++] = Integer.valueOf(str.substring(i, i + 2), 16).byteValue();
        }
        return response;
    }
}

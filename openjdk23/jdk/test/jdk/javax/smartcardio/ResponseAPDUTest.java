/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

/*
 * @test
 * @bug 8049021 8255546
 * @summary Construct ResponseAPDU from byte array and check NR< SW, SW1, SW2 and toString
 * @run testng ResponseAPDUTest
 */
import javax.smartcardio.ResponseAPDU;
import static org.testng.Assert.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ResponseAPDUTest {

    static final byte[] R1 = {(byte) 0x07, (byte) 0xA0, (byte) 0x00,
        (byte) 0x00, (byte) 0x00, (byte) 0x62, (byte) 0x81, (byte) 0x01,
        (byte) 0x04, (byte) 0x01, (byte) 0x00, (byte) 0x00, (byte) 0x24,
        (byte) 0x05, (byte) 0x00, (byte) 0x0B, (byte) 0x04, (byte) 0xB0,
        (byte) 0x25, (byte) 0x90, (byte) 0x00};
    static final ResponseAPDU RAPDU = new ResponseAPDU(R1);
    static byte[] expectedData;
    static int expectedNr, expectedSw1, expectedSw2, expectedSw;
    static String expectedToString;

    @BeforeClass
    public static void setUpClass() throws Exception {
        //expected values for data,nr,sw1,sw2 and sw

        int apduLen = R1.length;
        expectedData = new byte[apduLen - 2];
        for (int i = 0; i < (apduLen - 2); i++) {
            expectedData[i] = R1[i];
        }

        expectedNr = expectedData.length;
        expectedSw1 = R1[apduLen - 2] & 0xff;
        expectedSw2 = R1[apduLen - 1] & 0xff;
        expectedSw = (expectedSw1 << 8) | expectedSw2;

        expectedToString = "ResponseAPDU: " + R1.length +
                " bytes, SW=" + Integer.toHexString(expectedSw);
    }

    @Test
    public static void test() {
        assertEquals(RAPDU.getBytes(), R1);
        assertEquals(RAPDU.getData(), expectedData);
        assertEquals(RAPDU.getNr(), expectedNr);
        assertEquals(RAPDU.getSW(), expectedSw);
        assertEquals(RAPDU.getSW1(), expectedSw1);
        assertEquals(RAPDU.getSW2(), expectedSw2);
        assertEquals(RAPDU.toString(), expectedToString);
    }
}

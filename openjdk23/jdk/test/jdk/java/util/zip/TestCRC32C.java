/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.util.zip.CRC32C;

/**
 * @test @summary Check that CRC-32C returns the expected CRC value for the
 * string 123456789
 * http://reveng.sourceforge.net/crc-catalogue/all.htm#crc.cat.crc-32c
 * @build ChecksumBase
 * @run main TestCRC32C
 */

public class TestCRC32C {

    public static void main(String[] args) {
        ChecksumBase.testAll(new CRC32C(), 0xE3069283L);
    }
}

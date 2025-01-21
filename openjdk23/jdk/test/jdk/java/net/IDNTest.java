/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */

import java.net.IDN;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/*
 * @test
 * @bug 7038838
 * @summary verify the behaviour of the methods on java.net.IDN class
 * @run junit IDNTest
 */
public class IDNTest {

    /*
     * Verify that various methods on the IDN class throw a NullPointerException
     * for any null parameter.
     */
    @Test
    public void testNullPointerException() throws Exception {
        assertThrows(NullPointerException.class, () -> IDN.toASCII(null));
        assertThrows(NullPointerException.class, () -> IDN.toASCII(null, 0));
        assertThrows(NullPointerException.class, () -> IDN.toUnicode(null));
        assertThrows(NullPointerException.class, () -> IDN.toUnicode(null, 0));
    }
}
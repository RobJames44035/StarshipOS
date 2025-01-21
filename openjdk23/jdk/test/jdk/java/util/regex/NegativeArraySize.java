/*
 * StarshipOS Copyright (c) 2019-2025. R.A. James
 */

/*
 * @test
 * @bug 8223174
 * @summary Pattern.compile() can throw confusing NegativeArraySizeException
 * @requires os.maxMemory >= 6g & vm.bits == 64 & !vm.musl
 * @run testng/othervm -Xms5G -Xmx5G NegativeArraySize
 */

import org.testng.annotations.Test;
import static org.testng.Assert.assertThrows;

import java.util.regex.Pattern;

public class NegativeArraySize {
    @Test
    public static void testNegativeArraySize() {
        assertThrows(OutOfMemoryError.class, () -> Pattern.compile("\\Q" + "a".repeat(42 + Integer.MAX_VALUE / 3)));
    }
}

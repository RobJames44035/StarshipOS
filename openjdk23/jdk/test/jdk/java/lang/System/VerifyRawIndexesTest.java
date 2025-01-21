/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

import jdk.internal.util.SystemProps.Raw;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;


/*
 * @test
 * @summary Test to verify that the SystemProps.Raw _xxx_NDX indices are unique and without gaps.
 * @modules java.base/jdk.internal.util:+open
 * @run testng VerifyRawIndexesTest
 */

@Test
public class VerifyRawIndexesTest {

    /**
     * Check that the Raw._*_NDX indexes are sequential and followed by
     * the FIXED_LENGTH value.
     * It verifies there there are no gaps or duplication in the declarations.
     */
    @Test
    void verifyIndexes() {
        Field[] fields = Raw.class.getDeclaredFields();
        int expectedModifiers = Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL;

        int next = 0;       // indexes start at zero
        int fixedLength = -1;
        for (Field f : fields) {
            try {
                int mods = f.getModifiers();
                String name = f.getName();
                if (((mods & expectedModifiers) == expectedModifiers) &&
                    (name.endsWith("_NDX") || name.equals("FIXED_LENGTH"))) {
                    f.setAccessible(true);
                    int ndx = f.getInt(null);
                    System.out.printf("%s: %s%n", name, ndx);
                    Assert.assertEquals(ndx, next, "index value wrong");
                    if (name.equals("FIXED_LENGTH")) {
                        fixedLength = next;     // remember for final check
                    }
                    next++;
                } else {
                    System.out.printf("Ignoring field: " + f);
                }
            } catch (IllegalAccessException iae) {
                Assert.fail("unexpected exception", iae);
            }
        }
        Assert.assertEquals(next - 1, fixedLength,
                "FIXED_LENGTH should be 1 greater than max of _NDX indexes");
    }
}

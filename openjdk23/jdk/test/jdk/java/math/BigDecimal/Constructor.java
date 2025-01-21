/*
 * StarshipOS Copyright (c) 1999-2025. R.A. James
 */

/*
 * @test
 * @bug 4259453 8200698
 * @summary Test constructors of BigDecimal
 * @library ..
 * @run testng Constructor
 */

import java.math.BigDecimal;
import org.testng.annotations.Test;

public class Constructor {
    @Test(expectedExceptions=NumberFormatException.class)
    public void stringConstructor() {
        BigDecimal bd = new BigDecimal("1.2e");
    }

    @Test(expectedExceptions=NumberFormatException.class)
    public void charArrayConstructorNegativeOffset() {
        BigDecimal bd = new BigDecimal(new char[5], -1, 4, null);
    }

    @Test(expectedExceptions=NumberFormatException.class)
    public void charArrayConstructorNegativeLength() {
        BigDecimal bd = new BigDecimal(new char[5], 0, -1, null);
    }

    @Test(expectedExceptions=NumberFormatException.class)
    public void charArrayConstructorIntegerOverflow() {
        try {
            BigDecimal bd = new BigDecimal(new char[5], Integer.MAX_VALUE - 5,
                6, null);
        } catch (NumberFormatException nfe) {
            if (nfe.getCause() instanceof IndexOutOfBoundsException) {
                throw new RuntimeException
                    ("NumberFormatException should not have a cause");
            } else {
                throw nfe;
            }
        }
    }

    @Test(expectedExceptions=NumberFormatException.class)
    public void charArrayConstructorIndexOutOfBounds() {
        BigDecimal bd = new BigDecimal(new char[5], 1, 5, null);
    }
}

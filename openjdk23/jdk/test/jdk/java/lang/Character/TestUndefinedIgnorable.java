/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug 4453719
 * @author John O'Conner
 * @summary Undefined character values should not be ignorable identifiers.
 */

public class TestUndefinedIgnorable {
    static int endValue = 0xFFFF;

    public static void main(String[] args) {

        for (int ch=0x0000; ch <= endValue; ch++) {
            if (!Character.isDefined((char)ch) &&
                    Character.isIdentifierIgnorable((char)ch)) {
                throw new RuntimeException("Char value " + Integer.toHexString((char)ch));
            }
        }
        System.out.println("Passed.");

    }

}

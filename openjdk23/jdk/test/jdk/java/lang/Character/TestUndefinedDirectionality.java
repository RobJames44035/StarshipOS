/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug     4427146
 * @summary Undefined char values should have DIRECTIONALITY_UNDEFINED.
 * @author  John O'Conner
 */

public class TestUndefinedDirectionality {

    public static void main(String[] args) {
        int failures = 0;
        for (int ch=0x0000;ch <= 0xFFFF; ch++) {
            if (!Character.isDefined((char)ch)) {
                byte direction = Character.getDirectionality((char)ch);
                if (direction != Character.DIRECTIONALITY_UNDEFINED) {
                    System.err.println("Fail: \\u" + Integer.toString(ch, 16));
                    failures++;
                }
            }
        }
        if (failures != 0) {
            throw new RuntimeException("TestUndefinedDirectionality: failed.");
        } else {
            System.out.println("Passed.");
        }


    }
}

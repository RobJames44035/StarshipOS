/*
 * StarshipOS Copyright (c) 2018-2025. R.A. James
 */

/*
 * @test
 * @bug     4427146
 * @summary Some char values that are Unicode spaces are non-breaking. These
 *          should not be Java whitespaces.
 * @author  John O'Conner
 */

public class TestWhiteSpace {

    public static void main(String[] args) {
        // These values should NOT be whitespace
        char[] whiteSpace = {'\u00A0', '\u2007', '\u202F'};

        for (int x=0;x<whiteSpace.length;x++) {
            if (Character.isWhitespace(whiteSpace[x])) {
                throw new RuntimeException("Invalid whitespace: \\u" +
                    Integer.toString((int)whiteSpace[x], 16));
            }
        }
        System.out.println("Passed.");
    }
}

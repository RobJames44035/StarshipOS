/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4096273
   @summary new String("").indexOf("") must give 0, not -1
   @author Anand Palaniswamy
 */
public class IndexOfEmptyInEmpty {
    public static void main(String[] args) throws Exception {
        int result = new String("").indexOf("");
        if (result != 0) {
            throw new Exception("new String(\"\").indexOf(\"\") must be 0, but got " + result);
        }
    }
}

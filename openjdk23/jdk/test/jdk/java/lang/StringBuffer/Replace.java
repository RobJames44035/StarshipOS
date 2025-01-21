/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
   @bug 4174396
   @summary Use replace to append chars; No OutOfMemoryException should result
*/

public class Replace {
    public static void main(String[] arg) throws Exception {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 200; i++) {
            sb.replace(i, i+1, "a");
        }
    }
}

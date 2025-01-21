/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

import javax.print.attribute.standard.PageRanges;

/*
 * @test
 * @bug 4433126 4433096
 * @key printer
 * @summary  The line "ERROR: <message>" should NOT appear.
 * @run main PageRangesException
 */

public class PageRangesException {
    public static void main(String[] args) throws Exception {
        // test 4433126
        try {
            PageRanges pr = new PageRanges("0:22");
            throw new RuntimeException("ERROR: no exceptions");
        } catch (IllegalArgumentException ie) {
            System.out.println("OKAY: IllegalArgumentException " + ie);
        }

        // test 4433096
        try {
            int[][] m = null;
            PageRanges pr = new PageRanges(m);
            throw new RuntimeException("ERROR: NullPointerException expected");
        } catch (IllegalArgumentException ie) {
            throw new RuntimeException("ERROR: IllegalArgumentException", ie);
        } catch (NullPointerException e) {
            System.out.println("OKAY: NullPointerException");
        }
    }
}

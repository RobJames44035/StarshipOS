/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.print.PageFormat;

/*
 * @test
 * @bug 4199506
 * @summary Verify PageFormat.setPaper(null) throws NullPointerException
 *          as specified
 * @run main NullPaper
 */
public final class NullPaper {
    public static void main(String[] args) {
        try {
            /* Setting the paper to null should throw an exception.
             * The bug was the exception was not being thrown.
             */
            new PageFormat().setPaper(null);

            throw new RuntimeException("NullPointerException is expected "
                                       + "but not thrown");
        } catch (NullPointerException e) {
            System.out.println("NullPointerException caught - test passes");
        }
    }
}

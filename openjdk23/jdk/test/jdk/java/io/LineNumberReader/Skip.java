/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/* @test
 * @bug 4091789 4173233
 * @summary Check if LineNumberReader will skip right number of characters and
 *          also check for negative values
 */

import java.io.*;

public class Skip {

    public static void main(String[] args) throws Exception {

        int linenum = 0;
        long nchars = 164 * 50;

        File f = new File(System.getProperty("test.src", "."),
            "SkipInput.txt");
        LineNumberReader reader = new LineNumberReader(new FileReader(f));

        boolean testFailed = false;
        try {
            reader.skip(-10);
            testFailed = true;
        } catch (IllegalArgumentException e) {
        }
        catch (Exception e) {
            testFailed = true;
        }
        if (testFailed)
            throw new Exception("Failed test: Negative value for skip()");

        long realnum = reader.skip(nchars);
        linenum = reader.getLineNumber();

        if (linenum != 164) {
            throw new Exception("Failed test: Should skip 164, really skipped "
                                + linenum + "lines");
        }
    }
}

/*
 * StarshipOS Copyright (c) 2016-2025. R.A. James
 */


import java.io.File;
import java.io.FileReader;
import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/* @test
   @bug 8078268
   @summary  javax.swing.text.html.parser.Parser parseScript incorrectly optimized
   @run main bug8078268
*/
public class bug8078268 {
    private static final float tf = Float.parseFloat(System.getProperty("test.timeout.factor", "1.0"));
    private static final long TIMEOUT = 10_000 * (long)tf;

    private static final String FILENAME = "slowparse.html";

    private static final CountDownLatch latch = new CountDownLatch(1);
    private static volatile Exception exception;

    public static void main(String[] args) throws Exception {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HTMLEditorKit htmlKit = new HTMLEditorKit();
                Document doc = htmlKit.createDefaultDocument();
                try {
                    htmlKit.read(new FileReader(getAbsolutePath()), doc, 0);
                } catch (Exception e) {
                    exception = e;
                }
                latch.countDown();
            }
        });

        if (!latch.await(TIMEOUT, MILLISECONDS)) {
            throw new RuntimeException("Parsing takes too long. Current timeout is " + TIMEOUT);
        }
        if (exception != null) {
            throw exception;
        }
    }

    private static String getAbsolutePath() {
        return System.getProperty("test.src", ".")
               + File.separator + FILENAME;
    }
}

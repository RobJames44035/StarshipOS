/*
 * StarshipOS Copyright (c) 2015-2025. R.A. James
 */


import java.awt.Toolkit;
import java.net.URL;

/*
 * @test
 * @bug 8078165
 * @run main bug8078165
 * @summary NPE when attempting to get image from toolkit
 */
public final class bug8078165 {

    public static void main(final String[] args) throws Exception {
        // The method shouldn't throw NPE
        Toolkit.getDefaultToolkit().getImage(new URL("file://./dummyImage@2x.png"));
        Toolkit.getDefaultToolkit().getImage("./dummyImage@2x.png");
    }
}

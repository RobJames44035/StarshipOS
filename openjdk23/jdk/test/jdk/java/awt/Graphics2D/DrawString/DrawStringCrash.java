/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

/*
 * @test
 * @bug 8043508 8080084
 * @summary Drawing a very long string crashes VM
 * @run main/othervm DrawStringCrash
 */

import java.awt.*;
import java.awt.image.*;

public class DrawStringCrash {

    public static void main(String[] args) {
        StringBuffer sb = new StringBuffer();
        String s = "abcdefghijklmnopqrstuzwxyz";
        for (int x = 0; x < 100000 ; x++) {
           sb.append(s);
        }
        // Now have a string which uses approx 5Mb memory
        // Loop again drawing doubling each time until
        // we reach 8 billion chars or get OOME which means we can't
        // go any further.
        // Often there is no crash because Java OOM happens
        // long before native heap runs out.
        long maxLen = 8L * 1024 * 1024 * 1024;
        int len = sb.length();

        BufferedImage bi =
            new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bi.createGraphics();
        while (len < maxLen) {
            try {
                g2d.drawString(sb.toString(), 20, 20);
                sb.append(sb);
                len *= 2;
            } catch (OutOfMemoryError e) {
                return;
            }
        }
        return;
    }
}

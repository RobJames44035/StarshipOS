/*
 * StarshipOS Copyright (c) 2014-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @bug     8032788
 * @summary Checks that null filename argument is processed correctly
 *
 * @run     main ImageIconHang
 */
public class ImageIconHang {
    public static void main(String[] args) throws Exception {
        Image image = Toolkit.getDefaultToolkit().getImage((String) null);
        MediaTracker mt = new MediaTracker(new Component() {});
        mt.addImage(image, 1);
        mt.waitForID(1, 5000);

        int status = mt.statusID(1, false);

        System.out.println("Status: " + status);

        if (status != MediaTracker.ERRORED) {
            throw new RuntimeException("MediaTracker.waitForID() hung.");
        }
    }
}

/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check whether CardLayout constructor and methods do not throw exceptions
 *          in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessCardLayout
 */

public class HeadlessCardLayout {

    public static void main(String args[]) {
        CardLayout cl;
        cl = new CardLayout();
        cl = new CardLayout(10, 10);
        cl.getHgap();
        cl.setHgap(10);
        cl.getVgap();
        cl.setVgap(10);
    }
}

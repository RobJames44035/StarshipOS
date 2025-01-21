/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */

import java.awt.*;

/*
 * @test
 * @summary Check that FlowLayout constructor and method do not throw unexpected
  *         exceptions in headless mode
 * @run main/othervm -Djava.awt.headless=true HeadlessFlowLayout
 */

public class HeadlessFlowLayout {

    public static void main(String args[]) {
        FlowLayout bs = new FlowLayout();
        bs.getHgap();
        bs.setHgap(10);
        bs.getVgap();
        bs.setVgap(10);
    }
}

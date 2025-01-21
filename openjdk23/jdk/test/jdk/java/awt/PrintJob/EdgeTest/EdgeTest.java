/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */

/**
 * @test
 * @bug 4092755
 * @key printer
 * @summary Verifies that (0, 0) is the upper-left corner of the page, not
 *          the upper-left corner adjusted for the margins.
 * @run main/manual EdgeTest
 */

import java.awt.*;
import java.awt.event.*;

public class EdgeTest extends Panel {
    public void init() {
        Frame f = new Frame("EdgeTest");
        f.setSize(50, 50);
        f.addWindowListener( new WindowAdapter() {
                                    public void windowClosing(WindowEvent ev) {
                                        System.exit(0);
                                    }
                                }
                            );
        f.setVisible(true);
        JobAttributes job = new JobAttributes();
        job.setDialog(JobAttributes.DialogType.NONE);
        PrintJob pj = getToolkit().getPrintJob(f, "EdgeTest", job, null);
        if (pj != null) {
            Graphics g = pj.getGraphics();
            Dimension d = pj.getPageDimension();
            g.setColor(Color.black);
            g.setFont(new Font("Serif", Font.PLAIN, 12));

            //top
            g.drawLine(0, 0, d.width, 0);

            //left
            g.drawLine(0, 0, 0, d.height);

            //bottom
            g.drawLine(0, d.height, d.width, d.height);

            //right
            g.drawLine(d.width, 0, d.width, d.height);

            g.drawString("This page should have no borders!",
                         d.width / 2 - 100, d.height / 2 - 10);
            g.dispose();
            pj.end();
        }
    }

    public static void main(String[] args) {
        new EdgeTest().init();
    }
}

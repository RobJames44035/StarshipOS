/*
 * StarshipOS Copyright (c) 2009-2025. R.A. James
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Point;

/**
 * An interface provides a set of custom cursors
 */

public interface MyCursor {
    public static final java.awt.Cursor NO_DROP = Toolkit.getDefaultToolkit().createCustomCursor(
        new ImageGenerator(32, 32, new Color(0xff, 0xff, 0xff, 0x00) ) {
                @Override public void paint(Graphics gr) {
                    gr.setColor(Color.GREEN);
                    ((Graphics2D)gr).setStroke(new BasicStroke(3));

                    gr.translate(width/2, height/2);
                    int R = width/4;
                    gr.drawOval(-R, -R, 2*R, 2*R);
                    gr.drawLine(-R, R, R, -R);
                }
            }.getImage(),
            new Point(0, 0),
            "My NoDrop Cursor"
    );
    public static final java.awt.Cursor MOVE = Toolkit.getDefaultToolkit().createCustomCursor(
        new ImageGenerator(32, 32, new Color(0xff, 0xff, 0xff, 0x00) ) {
                @Override public void paint(Graphics gr) {
                    gr.setColor(Color.GREEN);
                    ((Graphics2D)gr).setStroke(new BasicStroke(3));

                    gr.drawLine(0, 0, width, height);
                    gr.drawLine(0, 0, width/2, 0);
                    gr.drawLine(0, 0, 0, height/2);
                }
            }.getImage(),
            new Point(0, 0),
            "My Move Cursor"
    );
    public static final java.awt.Cursor COPY = Toolkit.getDefaultToolkit().createCustomCursor(
        new ImageGenerator(32, 32, new Color(0xff, 0xff, 0xff, 0x00) ) {
                @Override public void paint(Graphics gr) {
                    gr.setColor(Color.GREEN);
                    ((Graphics2D)gr).setStroke(new BasicStroke(3));
                    //arrow
                    gr.drawLine(0, 0, width/2, height/2);
                    gr.drawLine(0, 0, width/2, 0);
                    gr.drawLine(0, 0, 0, height/2);
                    //plus
                    gr.drawRect(width/2 - 1, height/2 -1, width/2 - 1, height/2 - 1);
                    gr.drawLine(width*3/4 - 1, height/2 - 1, width*3/4 - 1, height);
                    gr.drawLine(width/2 - 1, height*3/4 - 1, width, height*3/4 - 1);
                 }
            }.getImage(),
            new Point(0, 0),
            "My Copy Cursor"
    );
}


/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package java2d.demos.Images;


import java.awt.*;
import javax.swing.JButton;
import java.awt.image.ImageObserver;
import java2d.AnimatingSurface;
import java2d.DemoPanel;


/**
 * Animated gif with a transparent background.
 */
@SuppressWarnings("serial")
public class DukeAnim extends AnimatingSurface implements ImageObserver {

    private static Image agif, clouds;
    private static int aw, ah, cw;
    private int x;
    private JButton b;

    @SuppressWarnings("LeakingThisInConstructor")
    public DukeAnim() {
        setBackground(Color.white);
        clouds = getImage("clouds.jpg");
        agif = getImage("duke.running.gif");
        aw = agif.getWidth(this) / 2;
        ah = agif.getHeight(this) / 2;
        cw = clouds.getWidth(this);
        dontThread = true;
    }

    @Override
    public void reset(int w, int h) {
        b = ((DemoPanel) getParent()).tools.startStopB;
    }

    @Override
    public void step(int w, int h) {
    }

    @Override
    public void render(int w, int h, Graphics2D g2) {
        if ((x -= 3) <= -cw) {
            x = w;
        }
        g2.drawImage(clouds, x, 10, cw, h - 20, this);
        g2.drawImage(agif, w / 2 - aw, h / 2 - ah, this);
    }

    @Override
    public boolean imageUpdate(Image img, int infoflags,
            int x, int y, int width, int height) {
        if (b.isSelected() && (infoflags & ALLBITS) != 0) {
            repaint();
        }
        if (b.isSelected() && (infoflags & FRAMEBITS) != 0) {
            repaint();
        }
        return isShowing();
    }

    public static void main(String[] s) {
        createDemoFrame(new DukeAnim());
    }
}

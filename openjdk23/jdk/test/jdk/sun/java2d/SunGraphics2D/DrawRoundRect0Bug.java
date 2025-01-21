/*
 * StarshipOS Copyright (c) 2001-2025. R.A. James
 */

/*
 * @test
 * @bug 4515761
 * @summary verify that drawRoundRect produces correct output for 0 w/h
 */

import java.awt.Color;
import static java.awt.Color.*;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class DrawRoundRect0Bug {

    public static void main(String argv[]) {
        BufferedImage img = new BufferedImage(250, 250, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        g.setColor(white);
        g.fillRect(0, 0, img.getWidth(), img.getHeight());

        g.setColor(green);
        g.drawLine(150, 90, 150, 110);
        if (img.getRGB(150, 100) != green.getRGB()) {
            throw new RuntimeException("Vertical line not green");
        }

        g.setColor(blue);
        g.drawRoundRect(160, 90, 0, 20, 4, 4);
        if (img.getRGB(160, 100) != blue.getRGB()) {
            throw new RuntimeException("Vertical (ie zero width) round rect not blue");
        }

        g.setColor(green);
        g.drawLine(150, 140, 170, 140);
        if (img.getRGB(160, 140) != green.getRGB()) {
            throw new RuntimeException("Horizontal line not green");
        }

        g.setColor(blue);
        g.drawRoundRect(150, 150, 20, 0, 4, 4);
        if (img.getRGB(160, 150) != blue.getRGB()) {
            throw new RuntimeException("Horizontal (ie zero height) round rect not blue");
        }
    }

}

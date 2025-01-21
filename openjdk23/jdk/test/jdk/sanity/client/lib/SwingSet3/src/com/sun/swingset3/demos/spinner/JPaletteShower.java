/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package com.sun.swingset3.demos.spinner;

import javax.swing.*;
import java.awt.*;

/**
 * @author Mikhail Lapshin
 */
public class JPaletteShower extends JComponent {

    private Palette palette;

    public JPaletteShower(Palette palette, int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setMinimumSize(new Dimension(width, height));
        this.palette = palette;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int w = getSize().width;
        int h = getSize().height;
        int maxIndex = palette.getSize() - 1;
        double rate = (double) maxIndex / w;
        for (int x = 0; x < w; x++) {
            g.setColor(palette.getColor((int) (x * rate)));
            g.fillRect(x, 0, 1, h);
        }
    }

    public Palette getPalette() {
        return palette;
    }

    public void setPalette(Palette palette) {
        this.palette = palette;
        repaint();
    }
}

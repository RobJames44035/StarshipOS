/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package com.sun.swingset3.demos;

import java.awt.*;
import java.net.URI;
import java.io.IOException;
import javax.swing.*;

/**
 * @author Pavel Porvatov
 */
public class DemoUtilities {

    private DemoUtilities() {
        // Hide constructor
    }

    public static void setToplevelLocation(Window toplevel, Component component,
            int relativePosition) {

        Rectangle compBounds = component.getBounds();

        // Convert component location to screen coordinates
        Point p = new Point();
        SwingUtilities.convertPointToScreen(p, component);

        int x;
        int y;

        // Set frame location to be centered on panel
        switch (relativePosition) {
            case SwingConstants.NORTH: {
                x = (p.x + (compBounds.width / 2)) - (toplevel.getWidth() / 2);
                y = p.y - toplevel.getHeight();
                break;
            }
            case SwingConstants.EAST: {
                x = p.x + compBounds.width;
                y = (p.y + (compBounds.height / 2)) - (toplevel.getHeight() / 2);
                break;
            }
            case SwingConstants.SOUTH: {
                x = (p.x + (compBounds.width / 2)) - (toplevel.getWidth() / 2);
                y = p.y + compBounds.height;
                break;
            }
            case SwingConstants.WEST: {
                x = p.x - toplevel.getWidth();
                y = (p.y + (compBounds.height / 2)) - (toplevel.getHeight() / 2);
                break;
            }
            case SwingConstants.NORTH_EAST: {
                x = p.x + compBounds.width;
                y = p.y - toplevel.getHeight();
                break;
            }
            case SwingConstants.NORTH_WEST: {
                x = p.x - toplevel.getWidth();
                y = p.y - toplevel.getHeight();
                break;
            }
            case SwingConstants.SOUTH_EAST: {
                x = p.x + compBounds.width;
                y = p.y + compBounds.height;
                break;
            }
            case SwingConstants.SOUTH_WEST: {
                x = p.x - toplevel.getWidth();
                y = p.y + compBounds.height;
                break;
            }
            default:
            case SwingConstants.CENTER: {
                x = (p.x + (compBounds.width / 2)) - (toplevel.getWidth() / 2);
                y = (p.y + (compBounds.height / 2)) - (toplevel.getHeight() / 2);
            }
        }
        toplevel.setLocation(x, y);
    }

    public static boolean browse(URI uri) throws IOException {
        // Try using the Desktop api first
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.browse(uri);

            return true;
        } catch (SecurityException e) {
            e.printStackTrace();
        }

        return false;
    }
}

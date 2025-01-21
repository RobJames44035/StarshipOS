/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.data;

import java.awt.Color;
import java.util.Objects;

/**
 *
 * @author Thomas Wuerthinger
 */
public class InputNode extends Properties.Entity {

    private int id;

    public InputNode(InputNode n) {
        super(n);
        setId(n.id);
    }

    public InputNode(int id) {
        setId(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        InputNode other = (InputNode) obj;
        return id == other.id &&
                Objects.equals(getProperties(), other.getProperties());
    }

    @Override
    public String toString() {
        return "Node " + id + " " + getProperties().toString();
    }

    public void setCustomColor(Color color) {
        if (color != null) {
            String hexColor = String.format("#%08X", color.getRGB());
            getProperties().setProperty("color", hexColor);
        } else {
            getProperties().setProperty("color", null);
        }
    }

    public Color getCustomColor() {
        String hexColor = getProperties().get("color");
        if (hexColor != null) {
            try {
                String hex = hexColor.startsWith("#") ? hexColor.substring(1) : hexColor;
                int argb = (int) Long.parseLong(hex, 16);
                return new Color(argb, true);
            } catch (Exception ignored) {
                return null;
            }
        }
        return null;
    }
}

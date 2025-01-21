/*
 * StarshipOS Copyright (c) 2022-2025. R.A. James
 */
package com.sun.hotspot.igv.graph;

import com.sun.hotspot.igv.layout.Port;
import java.awt.Color;
import java.awt.Point;
import java.util.List;
import java.util.Objects;

public class BlockConnection implements Connection {

    private final Block sourceBlock;
    private final Block destinationBlock;
    private final String label;
    private List<Point> controlPoints;

    public BlockConnection(Block src, Block dst, String label) {
        this.sourceBlock = src;
        this.destinationBlock = dst;
        this.label = label;
    }

    public Color getColor() {
        return Color.BLUE;
    }

    public ConnectionStyle getStyle() {
        return ConnectionStyle.BOLD;
    }

    @Override
    public String getToolTipText() {
        StringBuilder builder = new StringBuilder();
        builder.append("B").append(sourceBlock.getInputBlock().getName())
               .append(" → B").append(destinationBlock.getInputBlock().getName());
        if (label != null) {
            builder.append(": ").append(label);
        }
        return builder.toString();
    }

    @Override
    public String toString() {
        return "BlockConnection('" + label + "', " + getFromCluster() + " to " + getToCluster() + ")";
    }

    @Override
    public Port getFrom() {
        return null;
    }

    @Override
    public Block getFromCluster() {
        return sourceBlock;
    }

    @Override
    public Port getTo() {
        return null;
    }

    @Override
    public Block getToCluster() {
        return destinationBlock;
    }

    @Override
    public List<Point> getControlPoints() {
        return controlPoints;
    }

    @Override
    public void setControlPoints(List<Point> list) {
        controlPoints = list;
    }

    @Override
    public boolean hasSlots() {
        return false;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BlockConnection that)) return false;
        return Objects.equals(this.sourceBlock, that.sourceBlock) &&
                Objects.equals(this.destinationBlock, that.destinationBlock) &&
                Objects.equals(this.label, that.label);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sourceBlock, destinationBlock, label);
    }
}

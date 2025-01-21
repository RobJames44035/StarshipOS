/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.graph;

import com.sun.hotspot.igv.data.InputBlock;
import com.sun.hotspot.igv.data.InputNode;
import com.sun.hotspot.igv.layout.Cluster;
import com.sun.hotspot.igv.layout.Vertex;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.*;

/**
 *
 * @author Thomas Wuerthinger
 */
public class Block implements Cluster {

    protected final InputBlock inputBlock;
    private Rectangle bounds;
    private final Diagram diagram;

    public Block(InputBlock inputBlock, Diagram diagram) {
        this.inputBlock = inputBlock;
        this.diagram = diagram;
    }

    public InputBlock getInputBlock() {
        return inputBlock;
    }

    public Set<? extends Cluster> getSuccessors() {
        Set<Block> succs = new HashSet<Block>();
        for (InputBlock b : inputBlock.getSuccessors()) {
            if (diagram.hasBlock(b)) {
                succs.add(diagram.getBlock(b));
            }
        }
        return succs;
    }

    public List<? extends Vertex> getVertices() {
        List<Vertex> vertices = new ArrayList<>();
        for (InputNode inputNode : inputBlock.getNodes()) {
            if (diagram.hasFigure(inputNode)) {
                vertices.add(diagram.getFigure(inputNode));
            }
        }
        return vertices;
    }

    public void setBounds(Rectangle r) {
        this.bounds = r;
    }

    @Override
    public void setPosition(Point p) {
        if (bounds != null) {
            bounds.setLocation(p);
        }
    }

    @Override
    public Point getPosition() {
        return bounds.getLocation();
    }

    @Override
    public Rectangle getBounds() {
        return bounds;
    }

    public int compareTo(Cluster o) {
        return toString().compareTo(o.toString());
    }

    @Override
    public String toString() {
        return inputBlock.getName();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Block other = (Block) obj;
        return inputBlock.equals(other.inputBlock);
    }

    @Override
    public int hashCode() {
        return inputBlock.hashCode();
    }
}


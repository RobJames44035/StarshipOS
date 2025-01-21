/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.hierarchicallayout;

import com.sun.hotspot.igv.layout.Cluster;
import com.sun.hotspot.igv.layout.Link;
import com.sun.hotspot.igv.layout.Port;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Thomas Wuerthinger
 */
public class InterClusterConnection implements Link {

    private final Port inputSlot;
    private final Port outputSlot;
    private List<Point> intermediatePoints;

    public InterClusterConnection(ClusterOutputSlotNode outputSlotNode, ClusterInputSlotNode inputSlotNode) {
        this.inputSlot = inputSlotNode.getInputSlot();
        this.outputSlot = outputSlotNode.getOutputSlot();
        this.intermediatePoints = new ArrayList<>();
    }

    public Port getTo() {
        return inputSlot;
    }

    public Port getFrom() {
        return outputSlot;
    }

    public Cluster getFromCluster() {
        return null;
    }

    public Cluster getToCluster() {
        return null;
    }

    public void setControlPoints(List<Point> p) {
        this.intermediatePoints = p;
    }

    public List<Point> getControlPoints() {
        return intermediatePoints;
    }

    @Override
    public String toString() {
        return "InterClusterConnection[from=" + getFrom() + ", to=" + getTo() + "]";
    }
}

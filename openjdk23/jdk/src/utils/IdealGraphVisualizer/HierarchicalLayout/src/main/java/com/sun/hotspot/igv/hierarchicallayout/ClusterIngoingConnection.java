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
public class ClusterIngoingConnection implements Link {

    private List<Point> controlPoints;
    private final Port inputSlot;
    private final Port outputSlot;

    public ClusterIngoingConnection(ClusterInputSlotNode inputSlotNode, Link c) {
        this.controlPoints = new ArrayList<>();

        inputSlot = c.getTo();
        outputSlot = inputSlotNode.getOutputSlot();
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
        this.controlPoints = p;
    }

    public List<Point> getControlPoints() {
        return controlPoints;
    }
}

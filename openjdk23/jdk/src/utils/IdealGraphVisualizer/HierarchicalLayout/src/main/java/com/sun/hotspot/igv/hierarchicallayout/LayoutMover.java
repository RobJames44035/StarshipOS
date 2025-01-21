/*
 * StarshipOS Copyright (c) 2024-2025. R.A. James
 */
package com.sun.hotspot.igv.hierarchicallayout;

import com.sun.hotspot.igv.layout.Vertex;
import java.awt.Point;
import java.util.Set;

public interface LayoutMover {
    /**
     * Moves a link by shifting its position along the X-axis.
     *
     * @param linkPos The current position of the link.
     * @param shiftX  The amount to shift the link along the X-axis.
     */
    void moveLink(Point linkPos, int shiftX);

    /**
     * Moves a set of vertices.
     *
     * @param movedVertices A set of vertices to be moved.
     */
    void moveVertices(Set<? extends Vertex> movedVertices);

    /**
     * Moves a single vertex.
     *
     * @param movedVertex The vertex to be moved.
     */
    void moveVertex(Vertex movedVertex);

    boolean isFreeForm();
}


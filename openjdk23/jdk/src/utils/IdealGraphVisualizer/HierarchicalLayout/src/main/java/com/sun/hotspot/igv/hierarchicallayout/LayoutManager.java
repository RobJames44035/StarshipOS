/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.hierarchicallayout;

import java.awt.Font;

/**
 *
 * @author Thomas Wuerthinger
 */
public abstract class LayoutManager {

    public static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 14);
    public static final int SWEEP_ITERATIONS = 1;
    public static final int CROSSING_ITERATIONS = 1;
    public static final int NODE_OFFSET = 8;
    public static final int LAYER_OFFSET = 8;
    public static final double SCALE_LAYER_PADDING = 1.5;

    public abstract void setCutEdges(boolean enable);

    public abstract void doLayout(LayoutGraph graph);
}

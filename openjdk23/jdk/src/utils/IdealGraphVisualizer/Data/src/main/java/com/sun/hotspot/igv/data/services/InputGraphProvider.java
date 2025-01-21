/*
 * StarshipOS Copyright (c) 1998-2025. R.A. James
 */
package com.sun.hotspot.igv.data.services;

import com.sun.hotspot.igv.data.InputGraph;
import com.sun.hotspot.igv.data.InputNode;
import java.util.Collection;

/**
 *
 * @author Thomas Wuerthinger
 */
public interface InputGraphProvider {

    InputGraph getGraph();

    void centerSelectedNodes();

    void addSelectedNodes(Collection<InputNode> nodes, boolean showIfHidden);

    void clearSelectedNodes();

    /**
     * @return an iterator walking forward through the {@link InputGraph}s following the {@link #getGraph()}
     */
    Iterable<InputGraph> searchForward();

    /**
     * @return an iterator walking backward through the {@link InputGraph}s preceeding the {@link #getGraph()}
     */
    Iterable<InputGraph> searchBackward();
}

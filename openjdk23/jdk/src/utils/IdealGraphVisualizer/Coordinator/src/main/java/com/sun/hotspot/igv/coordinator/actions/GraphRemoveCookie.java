/*
 * StarshipOS Copyright (c) 2011-2025. R.A. James
 */
package com.sun.hotspot.igv.coordinator.actions;

import com.sun.hotspot.igv.data.InputGraph;
import com.sun.hotspot.igv.view.DiagramViewModel;
import com.sun.hotspot.igv.view.EditorTopComponent;
import java.util.List;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

public class GraphRemoveCookie implements RemoveCookie {
    private final InputGraph graph;

    public GraphRemoveCookie(InputGraph graph) {
        this.graph = graph;
    }

    @Override
    public void remove() {
        List<InputGraph> list = graph.getGroup().getGraphs();
        WindowManager manager = WindowManager.getDefault();
        for (Mode m : manager.getModes()) {
            for (TopComponent t : manager.getOpenedTopComponents(m)) {
                if (t instanceof EditorTopComponent) {
                    DiagramViewModel model = ((EditorTopComponent) t).getModel();
                    if (!model.getGroup().getGraphs().contains(graph)) {
                        continue;
                    }
                    int firstPosition = model.getFirstPosition();
                    int secondPosition = model.getSecondPosition();
                    int targetPosition = list.indexOf(graph);
                    if (targetPosition == firstPosition || targetPosition == secondPosition) {
                        t.close();
                        continue;
                    }
                    if (targetPosition < firstPosition) {
                        firstPosition--;
                    }
                    if (targetPosition < secondPosition) {
                        secondPosition--;
                    }
                    model.setPositions(firstPosition, secondPosition);
                }
            }
        }
        graph.getGroup().removeElement(graph);
    }
}

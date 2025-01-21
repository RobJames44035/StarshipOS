/*
 * StarshipOS Copyright (c) 2025. R.A. James
 */
package com.sun.hotspot.igv.view;

import java.awt.BorderLayout;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import org.openide.windows.TopComponent;

/**
 * This TopComponent is displayed in the editor location if no graphs have been loaded
 * and allows the user to easily drag and drop graph files to be opened.
 */
public class PlaceholderTopComponent extends TopComponent {
    public PlaceholderTopComponent(DropTargetListener fileDropListener) {
        setLayout(new BorderLayout());
        JPanel container = new JPanel(new BorderLayout());
        container.add(new JLabel("Drop file here to open", SwingConstants.CENTER), BorderLayout.CENTER);
        container.setDropTarget(new DropTarget(container, fileDropListener));
        add(container, BorderLayout.CENTER);
        setName("Welcome");
    }
}

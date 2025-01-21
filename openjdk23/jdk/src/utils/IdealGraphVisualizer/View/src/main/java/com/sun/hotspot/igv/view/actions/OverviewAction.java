/*
 * StarshipOS Copyright (c) 2008-2025. R.A. James
 */
package com.sun.hotspot.igv.view.actions;

import com.sun.hotspot.igv.view.EditorTopComponent;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;
import org.openide.util.ImageUtilities;

/**
 *
 * @author Thomas Wuerthinger
 */
public class OverviewAction extends AbstractAction {
    private static final String SATELLITE_STRING = "satellite";
    private static final String SCENE_STRING = "scene";

    public OverviewAction(JPanel panel) {
        int keyCode = KeyEvent.VK_S;
        panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
                KeyStroke.getKeyStroke(keyCode, 0, false), SATELLITE_STRING);
        panel.getActionMap().put(SATELLITE_STRING,
                new AbstractAction(SATELLITE_STRING) {
                    @Override public void actionPerformed(ActionEvent e) {
                        OverviewAction.this.setSelected(true);
                    }
                });
        panel.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(
                KeyStroke.getKeyStroke(keyCode, 0, true), SCENE_STRING);
        panel.getActionMap().put(SCENE_STRING,
                new AbstractAction(SCENE_STRING) {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        OverviewAction.this.setSelected(false);
                    }
                });

        putValue(AbstractAction.SMALL_ICON, new ImageIcon(ImageUtilities.loadImage(iconResource())));
        putValue(Action.SELECTED_KEY, false);
        putValue(Action.SHORT_DESCRIPTION, "Show satellite view of whole graph (hold S-KEY)");
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        EditorTopComponent editor = EditorTopComponent.getActive();
        if (editor != null) {
            boolean selected = (boolean)getValue(SELECTED_KEY);
            editor.showSatellite(selected);
        }
    }

    public void setSelected(boolean selected) {
        EditorTopComponent editor = EditorTopComponent.getActive();
        if (editor != null) {
            putValue(Action.SELECTED_KEY, selected);
            editor.showSatellite(selected);
        }
    }

    protected String iconResource() {
        return "com/sun/hotspot/igv/view/images/overview.png";
    }
}

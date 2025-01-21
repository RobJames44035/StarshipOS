/*
 * StarshipOS Copyright (c) 2002-2025. R.A. James
 */

/*
 * @test
 * @key headful
 * @bug 4234761
 * @summary RGB values sholdn't be changed in transition to HSB tab
 * @author Oleg Mokhovikov
 */

import java.awt.Color;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;

public class Test4234761 implements PropertyChangeListener {
    private static final Color COLOR = new Color(51, 51, 51);

    public static void main(String[] args) {
        JColorChooser chooser = new JColorChooser(COLOR);
        JDialog dialog = Test4177735.show(chooser);

        PropertyChangeListener listener = new Test4234761();
        chooser.addPropertyChangeListener("color", listener); // NON-NLS: property name

        JTabbedPane tabbedPane = (JTabbedPane) chooser.getComponent(0);
        tabbedPane.setSelectedIndex(1); // HSB tab index

        if (!chooser.getColor().equals(COLOR)) {
            listener.propertyChange(null);
        }
        dialog.dispose();
    }

    public void propertyChange(PropertyChangeEvent event) {
        throw new Error("RGB value is changed after transition to HSB tab");
    }
}

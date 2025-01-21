/*
 * StarshipOS Copyright (c) 2000-2025. R.A. James
 */

/*
 * @test
 * @bug 4306909
 * @summary Verify bug fix for 4306909
 * @author Neal Gafter (gafter)
 *
 * @modules java.desktop
 * @run compile DefAssignNestedArg.java
 */

public class DefAssignNestedArg extends Object {
    private static final java.beans.PropertyChangeListener listener =
        new java.beans.PropertyChangeListener() {
            public void propertyChange (java.beans.PropertyChangeEvent ev) { }
            public void xwait(javax.swing.JComponent obj) {
                obj.removePropertyChangeListener(listener);
            }
    };
}

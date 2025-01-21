/*
 * StarshipOS Copyright (c) 2003-2025. R.A. James
 */
package javax.swing.plaf.synth;

import sun.swing.DefaultLookup;

import javax.swing.JComponent;
import javax.swing.UIManager;
import javax.swing.plaf.ComponentUI;

/**
 * SynthDefaultLookup redirects all lookup calls to the SynthContext.
 *
 * @author Scott Violet
 */
class SynthDefaultLookup extends DefaultLookup {
    public Object getDefault(JComponent c, ComponentUI ui, String key) {
        if (ui instanceof SynthOptionPaneUI) {
            Object value = super.getDefault(c, ui, key);
            if (value != null) {
                return value;
            }
        }
        if (!(ui instanceof SynthUI)) {
            Object value = super.getDefault(c, ui, key);
            return value;
        }
        SynthContext context = ((SynthUI)ui).getContext(c);
        Object value = context.getStyle().get(context, key);
        return value;
    }
}

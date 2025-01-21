/*
 * StarshipOS Copyright (c) 2007-2025. R.A. James
 */
package java2d;


import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * Panel that holds the Demo groups, Controls and Monitors for each tab.
 * It's a special "always visible" panel for the Controls, MemoryMonitor &
 * PerformanceMonitor.
 */
@SuppressWarnings("serial")
public class GlobalPanel extends JPanel implements ChangeListener {
    private final DemoInstVarsAccessor demoInstVars;
    private JPanel p;
    private int index;

    public GlobalPanel(DemoInstVarsAccessor demoInstVars) {
        this.demoInstVars = demoInstVars;

        setLayout(new BorderLayout());
        p = new JPanel(new GridBagLayout());
        EmptyBorder eb = new EmptyBorder(5, 0, 5, 5);
        BevelBorder bb = new BevelBorder(BevelBorder.LOWERED);
        p.setBorder(new CompoundBorder(eb, bb));
        J2Ddemo.addToGridBag(p, demoInstVars.getControls(), 0, 0, 1, 1, 0, 0);
        J2Ddemo.addToGridBag(p, demoInstVars.getMemoryMonitor(), 0, 1, 1, 1, 0, 0);
        J2Ddemo.addToGridBag(p, demoInstVars.getPerformanceMonitor(), 0, 2, 1, 1, 0, 0);
        add(demoInstVars.getIntro());
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        demoInstVars.getGroup()[index].shutDown(demoInstVars.getGroup()[index].getPanel());
        if (demoInstVars.getTabbedPane().getSelectedIndex() == 0) {
            demoInstVars.getMemoryMonitor().surf.stop();
            demoInstVars.getPerformanceMonitor().surf.stop();
            removeAll();
            add(demoInstVars.getIntro());
            demoInstVars.getIntro().start();
        } else {
            if (getComponentCount() == 1) {
                demoInstVars.getIntro().stop();
                remove(demoInstVars.getIntro());
                add(p, BorderLayout.EAST);
                if (demoInstVars.getMemoryCB().getState()) {
                    demoInstVars.getMemoryMonitor().surf.start();
                }
                if (demoInstVars.getPerfCB().getState()) {
                    demoInstVars.getPerformanceMonitor().surf.start();
                }
            } else {
                remove(demoInstVars.getGroup()[index]);
            }
            index = demoInstVars.getTabbedPane().getSelectedIndex() - 1;
            add(demoInstVars.getGroup()[index]);
            demoInstVars.getGroup()[index].setup(false);
        }
        revalidate();
    }
}
